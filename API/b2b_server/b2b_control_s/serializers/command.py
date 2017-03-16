from b2b_control_s.models.command import Command
from rest_framework import serializers

class CommandSerializer(serializers.ModelSerializer):
    class Meta:
        model = Command
        fields = ('id', 'characters', 'value')
