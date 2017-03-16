from django import forms


class LoginForm(forms.Form):
    username = forms.CharField(label='Your name')
    password = forms.CharField(label='Your password', widget=forms.PasswordInput())
