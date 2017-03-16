"""b2b_client URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.10/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url
from django.views.generic import TemplateView
from b2b_control.decorators import auth_required
from b2b_control import views

app_name = 'b2b_control'

urlpatterns = [
       url(r'^$', TemplateView.as_view(template_name='index.html'), name='index'),
       url(r'^accounts/login/$', views.login, name='login'),
       url(r'^accounts/logout/$', views.logout, name='logout'),
       url(r'^control/$', views.control, name='control'),
       url(r'^command/$', views.command, name='command'),
       url(r'^image/$', views.image, name='image'),
]
