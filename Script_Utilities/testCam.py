import sys
import pygame
import pygame.camera

pygame.init()
pygame.camera.init()

#create fullscreen display 640x480
# screen = pygame.display.set_mode((640,480),0)

#find, open and start low-res camera
cam_list = pygame.camera.list_cameras()
webcam = pygame.camera.Camera(cam_list[1],(640,480))
webcam.start()

test = 0


while test < 1:
    #grab image, scale and blit to screen
    imagen = webcam.get_image()
    imagen = pygame.transform.scale(imagen,(640,480))
    # screen.blit(imagen,(0,0))
    pygame.image.save(imagen,"filename.jpg")

    #draw all updates to display
    # pygame.display.update()

    test+=1

    # check for quit events
    # for event in pygame.event.get():
    #     if event.type == pygame.QUIT:
    #         webcam.stop()
    #         pygame.quit()
    #         sys.exit()
# import pygame
# import pygame.camera
# import time
#
# pygame.camera.init()
# # pygame.camera.list_camera() #Camera detected or not
# cam = pygame.camera.Camera("/dev/video1",(640,480))
# cam.start()
# time.sleep(1)
# img = cam.get_image()
# img = pygame.transform.scale(img,(640,480))
# pygame.image.save(img,"filename.jpg")

# import numpy as np
# import cv2
# import sys
#
# cap = cv2.VideoCapture(0)
# cap.open(0)
#
# while(True):
#     # Capture frame-by-frame
#     ret, frame = cap.read()
#
#     if ret != True :
#         print "error on the capture"
#         sys.exit(1)
#
#     # Our operations on the frame come here
#     gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
#
#     # Display the resulting frame
#     cv2.imshow('frame',gray)
#     if cv2.waitKey(1) & 0xFF == ord('q'):
#         break
#
# # When everything done, release the capture
# cap.release()
# cv2.destroyAllWindows()
