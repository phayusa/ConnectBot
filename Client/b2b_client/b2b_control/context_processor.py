from django.conf import settings


def global_settings(request):
    return {
        'COMMAND_URL': '{0}:{1}{2}'.format(
            settings.API['client']['URL'],
            settings.API['client']['PORT'],
            settings.API['client']['ROUTES']['command']
            ),
        'IMAGE_URL': '{0}:{1}{2}'.format(
            settings.API['client']['URL'],
            settings.API['client']['PORT'],
            settings.API['client']['ROUTES']['image']
            ),

    }
