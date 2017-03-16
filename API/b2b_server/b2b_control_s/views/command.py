from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView
from b2b_control_s.models import Command
from b2b_control_s.serializers import CommandSerializer


class CommandView(APIView):
    # Classic get
    def get(self, request, format=None):
        command = Command.objects.all()
        serializer = CommandSerializer(command, many=True)
        json = serializer.data
        command.delete()
        return Response(json)

    # Communicate with the serial connection with RPC
    def post(self, request, format=None):
        serializer = CommandSerializer(data=request.data)
        if serializer.is_valid():
            message = serializer.save()
            print 'RPC : ' + message.characters
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
