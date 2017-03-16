import pygame
import pygame.camera
from PIL import Image
from rest_framework import status
from rest_framework.response import Response
from django.http import *


def get_captureFromCam():
    pygame.init()
    pygame.camera.init()

    # find, open and start low-res camera
    cam_list = pygame.camera.list_cameras()
    webcam = pygame.camera.Camera(cam_list[0], (250, 250))
    webcam.start()

    # The first image is always bad so we take the second
    for i in range(0,2):
        imagen = webcam.get_image()
    imagen = pygame.transform.scale(imagen, (250, 250))
    pygame.image.save(imagen, "img.jpg")
    webcam.stop()


def ImageView(request):

    # if request.GET.get('Authorization'):
    get_captureFromCam()

    # send the photo by a request
    try:
        with open("img.jpg", "rb") as f:
            return HttpResponse(f.read(), content_type="image/jpeg")
    except IOError:
        red = Image.new('RGBA', (1, 1), (0, 0, 0, 0))
        response = HttpResponse(content_type="image/jpeg")
        red.save(response, "JPEG")
        return response
    # return Response(status=status.HTTP_401_UNAUTHORIZED)
