package org.zlibrary.core.application;

import java.util.ArrayList;

import org.zlibrary.core.view.ZLViewWidget;

abstract public class ZLApplicationWindow {
	private ZLApplication myApplication;
	private boolean myToggleButtonLock;

	protected ZLApplicationWindow(ZLApplication application) {
		myApplication = application;
		myApplication.setWindow(this);
		myToggleButtonLock = false;
	}

	public ZLApplication getApplication() {
		return myApplication;
	}

	protected void init() {
		myApplication.setViewWidget(createViewWidget());

		final ZLApplication.Toolbar toolbar = myApplication.getToolbar();
		if (toolbar != null) {
			final int size = toolbar.size();
			for (int i = 0; i < size; ++i) {
				addToolbarItem(toolbar.getItem(i));
			}
		}

		initMenu();
	}
	
	abstract protected void initMenu();

	public void onButtonPress(ZLApplication.Toolbar.ButtonItem button) {
		/*
		if (myToggleButtonLock) {
			return;
		}
		if (button.isToggleButton()) {
			myToggleButtonLock = true;
			if (button.isPressed()) {
				setToggleButtonState(button);
				myToggleButtonLock = false;
				return;
			} else {
				button.press();
				ZLApplication.Toolbar.ButtonGroup group = button.getButtonGroup();
				final ArrayList<ZLApplication.Toolbar.ButtonItem> items = group.Items;
				final int size = items.size();
				for (int i = 0; i < size; ++i) {
					setToggleButtonState(items.get(i));
				}
			}
			myToggleButtonLock = false;
		}
		*/
		
		
		getApplication().doAction(button.getActionId());
	}
	
	public abstract void setToggleButtonState(ZLApplication.Toolbar.ButtonItem item);
	
	public abstract void setToolbarItemState(ZLApplication.Toolbar.Item item, boolean visible, boolean enabled);
	
	abstract protected ZLViewWidget createViewWidget();
	
	abstract public void addToolbarItem(ZLApplication.Toolbar.Item item);

	protected void refresh() {
		final ZLApplication.Toolbar toolbar = myApplication.getToolbar();
		if (toolbar != null) {
			boolean enableToolbarSpace = false;
			ZLApplication.Toolbar.Item lastSeparator = null;
			final int size = toolbar.size();
			for (int i = 0; i < size; ++i) {
				final ZLApplication.Toolbar.Item item = toolbar.getItem(i);
				/*
				if (item instanceof ZLApplication.Toolbar.OptionEntryItem) {
				case OPTION_ENTRY:
				{
					boolean visible = ((OptionEntryItem)item.entry().isVisible())//((Toolbar.OptionEntryItem)**it).entry()->isVisible();
							if (visible) {
								if (lastSeparator != null) {
									setToolbarItemState(lastSeparator, true, true);
									lastSeparator = null;
								}
								enableToolbarSpace = true;
							}
							setToolbarItemState(item, visible, true);
						}
						break;
				} else */
				if (item instanceof ZLApplication.Toolbar.ButtonItem) {
					ZLApplication.Toolbar.ButtonItem button = (ZLApplication.Toolbar.ButtonItem)item;
					int id = button.getActionId();
	
					boolean visible = getApplication().isActionVisible(id);
					boolean enabled = getApplication().isActionEnabled(id);
    
					if (visible) {
						if (lastSeparator != null) {
							setToolbarItemState(lastSeparator, true, true);
							lastSeparator = null;
						}
						enableToolbarSpace = true;
					}
					/*
					if (!enabled && button.isPressed()) {
						ZLApplication.Toolbar.ButtonGroup group = button.getButtonGroup();
						group.press(null);
						getApplication().doAction(group.UnselectAllButtonsActionId);
						myToggleButtonLock = true;
						setToggleButtonState(button);
						myToggleButtonLock = false;
					}
					*/
					setToolbarItemState(item, visible, enabled);
				} else if (item instanceof ZLApplication.Toolbar.SeparatorItem) {
					if (enableToolbarSpace) {
						lastSeparator = item;
						enableToolbarSpace = false;
					} else {
						setToolbarItemState(item, false, true);
					}
					//break;
				}
			}
			
			if (lastSeparator != null) {
				setToolbarItemState(lastSeparator, false, true);
			}
		}
	}
	// TODO: change to pure virtual
	//virtual void present() {}

//*/
	abstract public void close();
	abstract public void setCaption(String caption);
	abstract public void setFullscreen(boolean fullscreen);
	abstract public boolean isFullscreen();

	abstract public boolean isFingerTapEventSupported();
	abstract public boolean isMousePresented();
	abstract public boolean isKeyboardPresented();
	abstract public boolean isFullKeyboardControlSupported();

/*
	virtual void grabAllKeys(bool grab) = 0;
	virtual void setHyperlinkCursor(bool) {}
*/
}
