import requests
from django.conf import settings
from django.urls import reverse
from b2b_control.exceptions import RedirectException


def header_token(token):
    """
    Set token in header
    :param token:
    :return Dict:
    """
    return {'Authorization': '{0} {1}'.format('JWT', token)}


def get_url(route_name, param=''):
    """
    Build api url with optionnal param
    :param route_name:
    :param param:
    :return string:
    """
    return '{0}:{1}{2}{3}'.format(
        settings.API['default']['URL'],
        settings.API['default']['PORT'],
        settings.API['default']['ROUTES'][route_name],
        param
    )


def get_call(url, params=None, headers=None):
    """
    Call api with GET method
    :param url:
    :param params:
    :param headers:
    :return:
    """
    response = requests.get(url, params=params, headers=headers, verify=False)

    if response.status_code == 401:
        raise RedirectException(reverse('b2b_control:logout'))

    return response


def post_call(url, params=None, json=None, headers=None):
    """
    Call api with POST method
    :param url:
    :param params:
    :param json:
    :param headers:
    :return:
    """
    if params is None:
        params = {}

    response = requests.post(url, data=params, json=json, headers=headers, verify=False)

    if response.status_code == 401:
        raise RedirectException(reverse('logout'))

    return response


def put_call(url, params=None, headers=None):
    """
    Call api with PUT method
    :param url:
    :param params:
    :param headers:
    :return:
    """
    if params is None:
        params = {}

    response = requests.put(url, data=params, headers=headers, verify=False)

    if response.status_code == 401:
        raise RedirectException(reverse('b2b_control:logout'))

    return response


def delete_call(url, headers=None):
    """
    Call api with DELETE method
    :param url:
    :param headers:
    :return:
    """
    response = requests.delete(url, headers=headers, verify=False)

    if response.status_code == 401:
        raise RedirectException(reverse('b2b_control:logout'))

    return response


def login(params):
    """
    Login user
    :param params:
    :return:
    """
    url = get_url('login')

    return post_call(url, params=params)


def get_user(user_name, token):
    """
    Retrieve user
    :param user_email:
    :param token:
    :return:
    """
    url = get_url('users', user_name)
    headers = header_token(token)

    return get_call(url, headers=headers)


def get_image(user_name, token):
    """
    Retrieve user
    :param user_email:
    :param token:
    :return:
    """
    url = get_url('image')
    headers = header_token(token)

    return get_call(url, headers=headers)


def post_command(params):
    """
    Retrieve user
    :param user_email:
    :param token:
    :return:
    """
    url = get_url('command')

    return post_call(url, params=params)
