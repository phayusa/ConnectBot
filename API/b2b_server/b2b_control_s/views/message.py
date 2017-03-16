from rest_framework import status
from rest_framework.views import APIView
from rest_framework.response import Response
from b2b_control_s.models import Message
from b2b_control_s.serializers import MessageSerializer


# Can be generic just useful to getting all the commands
class MessageList(APIView):
    # Classic get
    def get(self, request, format=None):
        msg = Message.objects.all()
        serializer = MessageSerializer(msg, many=True)
        json = serializer.data
        msg.delete()
        return Response(json)

    # Communicate with the serial connection with RPC
    def post(self, request, format=None):
        serializer = MessageSerializer(data=request.data)
        if serializer.is_valid():
            message = serializer.save()
            print 'Receive message : ' + message.message
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)