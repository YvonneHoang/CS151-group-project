Note of 4/28/2017 (Fri):
Code demonstrate how to freely position and draw on JComponents that are within a JPanel

Steps to make free positioning JComponent:

step 1: JPanel.setLayout(null);
step 2: Insets inset = JPanel.getInsets(); 
    //From Java: An Insets object is a representation of the borders of a container...
step 3: JComponent.setBounds(inset.left +x, inset.top +y, w, h);


For more info:
http://docs.oracle.com/javase/tutorial/uiswing/layout/none.html