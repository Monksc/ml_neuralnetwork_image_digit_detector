# ml_neuralnetwork_image_digit_detector
This is a digit detector I made in java from scratch. It uses a neural 
network, autocoder and a cluster to detect what digit is in a 25 by 25
pixel image of a digit. It starts of by training the data in an neural
network of 625 as the input layer then 100,10,100 as the hidden layers
and finally 625 is the output layer. The 625 is because the image is 25 
by 25 pixels which is 625. The neural network acts as an auto encoder to
take an image converts it to the middle of the network which has 10 doubles
the decodes this back to an image. The program works by taking that middle
layer of 10 doubles and check to see what is the closest 3 to it. I then
finds what number that those were and finds out what number itself is.