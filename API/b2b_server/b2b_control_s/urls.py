"""RobotServerAPI URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.10/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLc onf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url
from b2b_control_s.views.login import LoginView
from b2b_control_s.views.command import CommandView
from b2b_control_s.views.image import ImageView
from b2b_control_s.views.message import MessageList

urlpatterns = [
    url(r'^users/login/$', LoginView.as_view()),
    url(r'^command/$', CommandView.as_view()),
    url(r'^image/get/', ImageView),
    url(r'^message/$', MessageList.as_view()),
]
