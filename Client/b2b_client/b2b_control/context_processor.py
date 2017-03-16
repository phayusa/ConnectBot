from django.conf import settings


def global_settings(request):
    return {
        'COMMAND_URL': '{0}:{1}{2}'.format(
            settings.API['default']['URL'],
            settings.API['default']['PORT'],
            settings.API['default']['ROUTES']['command']
            ),
        'IMAGE_URL': '{0}:{1}{2}'.format(
            settings.API['default']['URL'],
            settings.API['default']['PORT'],
            settings.API['default']['ROUTES']['image']
            ),
    }
