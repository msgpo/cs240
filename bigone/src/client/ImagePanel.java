package client;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;

import java.util.*;
import shared.model.field;

public class ImagePanel extends JPanel{
	
	private DrawingComponent component;
	private JSlider slider;
	private IndexingListener il;
	
	public ImagePanel(IndexingListener IL) {
		il = IL;
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		component = new DrawingComponent();
		component.addIndexingListener(il);
		this.add(component);
		
		slider = new JSlider(1, 100, 20);
		slider.addChangeListener(sliderChangeListener);
		this.add(slider);
		
	}

	public void clear(){
		component.resetImage();
		component.noGrid();
	}

	public void zoomIn(){
		component.zoomIn();
	}
	public void zoomOut(){
		component.zoomOut();
	}
	public void setZoom(double z){
		component.setScale(z);
	}
	public void toggleInversion(){
		component.inversion();
	}
	public void setImage(BufferedImage i){
		component.setImage(i);
	}
	public void highlight(int rows, int x, int y,
			int height, LinkedList<field> fields){
		component.addGrid(rows, x, y, height, fields);
	}
	public void clearGrid(){
		component.noGrid();
	}
	public void setPos(int x, int y){
		component.setOrigin(x, y);
	}

		
	private ChangeListener sliderChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e){
			component.setScale(slider.getValue() * 0.05);
		}
	};

		
}
