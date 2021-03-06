from b2b_control.forms import LoginForm
from component.services.api import login as api_login
from component.services.api import get_image as api_get_image
from component.services.api import post_command as api_post_command
from b2b_control.decorators import auth_required
from django.shortcuts import render
from django.http import HttpResponseRedirect, HttpResponse
from django.core.urlresolvers import reverse


def login(request):
    if request.method == 'POST':
        form = LoginForm(request.POST)
        if form.is_valid():
            response = api_login(form.cleaned_data)
            if response.ok:
                user = response.json()
                request.session['user'] = user
                request.session['is_authenticated'] = True

                return HttpResponseRedirect(reverse('b2b_control:control'))

            return render(request, 'registration/login.html',
                          {'form': form, 'errors': response.json()['errors']['non_field_errors']})
    else:
        form = LoginForm()

    return render(request, 'registration/login.html', {'form': form})


def logout(request):
    try:
        del request.session['is_authenticated']
        del request.session['user']
    except KeyError:
        pass
    return HttpResponseRedirect(reverse('b2b_control:index'))


@auth_required
def control(request):
    return render(request, 'control/control.html')


@auth_required
def command(request):
    response = api_post_command(request.POST, request.session['user']['token'])

    if response.ok:
        return HttpResponse(response)

    return render(request, 'registration/login.html')


@auth_required
def image(request):

    response = api_get_image(request.session['user']['token'])
    if response.ok:
        return HttpResponse(response, content_type="image/jpeg")
    return render(request, 'registration/login.html')

