package client;

import shared.communication.*;
import shared.model.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;


public interface FacadeListener {
	public abstract void imageChanged(BufferedImage i);
	public abstract void newBatch(BatchState bs);
}


