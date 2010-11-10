package org.pentaho.ui.xul.gwt.tags.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.*;
import org.pentaho.gwt.widgets.client.ui.Draggable;
import org.pentaho.gwt.widgets.client.utils.ElementUtils;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.gwt.tags.GwtTree;


/**
 * User: nbaker
 * Date: Jul 13, 2010
 */
public class TreeItemWidget extends FlexTable implements HasAllMouseHandlers, Draggable {
  Label label = new Label();
  String text;
  private XulTreeItem tItem;
  private Image dragIndicator;


  public TreeItemWidget(){
    setWidth("100%");
    this.setStylePrimaryName("tree-item-custom-widget");
    this.setWidget(0,1,label);
    this.getCellFormatter().setWidth(0,1,"100%");
    ElementUtils.preventTextSelection(this.getElement());
  }

  public TreeItemWidget(XulTreeItem tItem){
    this();
    this.tItem = tItem;
  }

  public XulTreeItem getTreeItem(){
    return tItem;
  }

  public void setLabel(String label){
    this.text = label;
    this.label.setText(label);
  }
  
  public void setImage( Image img){
    this.setWidget(0,0,img);
  }


  /**
   * DND required methods below
   */
  public HandlerRegistration addMouseUpHandler( MouseUpHandler handler ) {
    return addDomHandler(handler, MouseUpEvent.getType());
  }

  public HandlerRegistration addMouseOutHandler( MouseOutHandler handler ) {
    return addDomHandler(handler, MouseOutEvent.getType());
  }

  public HandlerRegistration addMouseMoveHandler( MouseMoveHandler handler ) {
    return addDomHandler(handler, MouseMoveEvent.getType());
  }

  public HandlerRegistration addMouseWheelHandler( MouseWheelHandler handler ) {
    return addDomHandler(handler, MouseWheelEvent.getType());
  }

  public HandlerRegistration addMouseOverHandler( MouseOverHandler handler ) {
    return addDomHandler(handler, MouseOverEvent.getType());
  }

  public HandlerRegistration addMouseDownHandler( MouseDownHandler handler ) {
    return addDomHandler(handler, MouseDownEvent.getType());
  }


  private void makeDraggable(){
    dragIndicator = new Image(GWT.getModuleBaseURL()+"drop_invalid.png");
    setWidget(0, 0, dragIndicator);

    this.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
    addStyleDependentName("proxy");
  }

  public Widget makeProxy(Widget ele) {
    TreeItemWidget item = new TreeItemWidget();
    item.setLabel(getLabel());
    item.setWidth("20px");
    item.makeDraggable();
    return item;
  }

  public String getLabel() {
    return text;
  }

  public Object getDragObject() {
    return tItem != null ? tItem.getBoundObject() : null;
  }

  public void notifyDragFinished() {
    ((GwtTree) tItem.getTree()).notifyDragFinished(tItem);
  }

  public void setDropValid(boolean valid){
    if(valid){
      addStyleDependentName("proxy-valid");
      dragIndicator.setUrl(GWT.getModuleBaseURL()+"drop_valid.png");
    } else {
      removeStyleDependentName("proxy-valid");
      dragIndicator.setUrl(GWT.getModuleBaseURL()+"drop_invalid.png");
    }
  }

  public void highLightDrop(boolean highlight) {
    if(highlight){
      addStyleDependentName("drop-hover");
    } else {
      removeStyleDependentName("drop-hover");
    }
  }
}