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


public interface IndexingListener {
	public abstract void zoomChanged(double z);
	public abstract void scrollChanged(int x, int y);
	
}


