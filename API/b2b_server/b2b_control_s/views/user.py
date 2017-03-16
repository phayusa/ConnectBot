from rest_framework import viewsets
from rest_framework.response import Response
from django.contrib.auth.models import User
from b2b_control_s.permissions.user import *


class UserViewSet(viewsets.ViewSet):

    permission_classes = (IsPostOrIsAuthenticated, IsOwnerOrIsAdmin,)
    serializer_class = User

    def retrieve(self, request, email=None):
        serializer = self.serializer_class(User.objects.get(email=email, is_valide=True), context={'user': request.user})
        return Response(serializer.data)
