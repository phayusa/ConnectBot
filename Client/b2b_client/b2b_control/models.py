from django.db import models


class Message(models.Model):
    message = models.CharField(max_length=100)


class Command(models.Model):
    # character send
    characters = models.CharField(max_length=100, default='')
    # the force of the character (manage the speed)
    value = models.IntegerField(default=1)
