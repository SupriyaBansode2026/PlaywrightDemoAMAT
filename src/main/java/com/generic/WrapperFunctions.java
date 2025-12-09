package com.generic;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;
import javax.imageio.ImageIO;

/**
 * @ScriptName : WrapperFunctions
 * @Description :
 * @Author : Harshvardhan Yadav (SQS), Vikram Bendre (SQS), Indrjeet Chanvan
 *         (SQS)
 */

public class WrapperFunctions {
	private Page page;
	public Properties objConfig;
	public static String failureType = "NA";
	public ExceptionHandler exceptionHandler;
	String url =null;
	public Locator loc;
	public BrowserContext context;
	public WrapperFunctions(Pojo objPojo) {
		exceptionHandler = new ExceptionHandler();
		objConfig = objPojo.getObjConfig();
		page = objPojo.getPage();
	}

	/**
	 * @Method : waitForElementPresence
	 * @Description : This is wrapper method wait for element presence
	 * @param : locator - By identification of element
	 * @param : waitInSeconds - wait time
	 * @author : Harshvardhan Yadav (SQS)
	 */
	public boolean waitForElementPresence(String selector) {
	    try {
	      page.waitForSelector(selector, new Page.WaitForSelectorOptions()
	        .setState(WaitForSelectorState.ATTACHED)
	        .setTimeout(10000)); // 10 seconds
	      return true;
	    } catch (Exception exception) {
	      WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(
	        exception, checkPageisLoadedCompletely());
	      return false;
	    }
	  }
	
	public boolean waitTillUrlContains(String text) {
	    try {
	      // Wait up to 10 seconds for URL to contain the specified text
	      for (int i = 0; i < 100; i++) {
	        if (page.url().contains(text)) {
	          return true;
	        }
	        Thread.sleep(100); // wait 100ms before checking again
	      }
	      throw new RuntimeException("URL did not contain expected text within timeout");
	    } catch (Exception exception) {
	      WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(
	        exception, checkPageisLoadedCompletely());
	      return false;
	    }
	  }
	
	/**
	 * @Method : checkElementExistence
	 * @Description : This is wrapper method to check the existence of any web
	 *              element on the page
	 * @param : locator - By identification of element
	 * @param : waitInSeconds - wait time
	 * @return : - true if element present
	 * @author : Vikram Bendre (SQS)
	 */
	 public boolean waitForElementToBeClickable(String selector) {
		    try {
		      // Wait until the element is visible and enabled (clickable)
		      page.waitForSelector(selector, new Page.WaitForSelectorOptions()
		        .setState(WaitForSelectorState.VISIBLE)
		        .setTimeout(10000)); // 10 seconds

		      // Optionally check if the element is enabled
		      boolean isEnabled = page.locator(selector).isEnabled();
		      if (!isEnabled) {
		        throw new RuntimeException("Element is visible but not enabled");
		      }

		      return true;
		    } catch (Exception exception) {
		      WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(
		        exception,checkPageisLoadedCompletely());
		      exception.printStackTrace();
		      return false;
		    }
		  }

	/**
	 * @Method : checkElementDisplyed
	 * @Description : This is wrapper method to check the existence of any web
	 *              element on the page
	 * @param : locator - By identification of element
	 * @param : waitInSeconds - wait time
	 * @return : - true if element present
	 * @author : Harshvardhan Yadav (SQS)
	 */
	 public boolean checkElementDisplyed(String selector) {
	        try {
	            Locator locator = page.locator(selector);
	            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
	            return locator.isVisible();
	        } catch (Exception e) {
	            WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(e, checkPageisLoadedCompletely());
	            return false;
	        }
	    }
	/**
	 * @Method : click
	 * @Description : This is wrapper method to click on web element
	 * @param : locator - By identification of element
	 * @return : - true if click successful
	 * @author : Vikram Bendre(SQS)
	 */
	 public boolean click(String selector) {
	        try {
	            Locator locator = page.locator(selector);
	            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
	            locator.scrollIntoViewIfNeeded();
	            locator.click();
	            return true;
	        } catch (Exception e) {
	            WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(e, checkPageisLoadedCompletely());
	            e.printStackTrace();
	            return false;
	        }
	    }

	/**
	 * @Method : scrollAndclick
	 * @Description : This is wrapper method to click on web element
	 * @param : locator - By identification of element
	 * @return : - true if click successful
	 * @author : Richa(SQS)
	 */
	 public boolean scrollAndClick(String selector) {
	        try {
	            Locator locator = page.locator(selector);
	            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

	            // Scroll element into view
	            locator.scrollIntoViewIfNeeded();

	            // Scroll page upward (simulate scroll to top)
	            page.evaluate("window.scrollTo(0, -document.body.scrollHeight);");

	            // Click the element
	            locator.click();

	            return true;
	        } catch (Exception e) {
	            WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(e, checkPageisLoadedCompletely());
	            e.printStackTrace();
	            return false;
	        }
	    }

	/**
	 * @Method : scrollAndclick
	 * @Description : This is wrapper method to click on web element
	 * @param : locator - By identification of element
	 * @return : - true if click successful
	 * @author : Richa(SQS)
	 */
	 public boolean clickWithoutScroll(String selector) {
	        try {
	            Locator locator = page.locator(selector);
	            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
	            locator.click(new Locator.ClickOptions().setForce(true)); // Bypasses visibility/scroll constraints
	            return true;
	        } catch (Exception e) {
	            WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(e, checkPageisLoadedCompletely());
	            e.printStackTrace();
	            return false;
	        }
	    }

	/**
	 * @Method : doubleClick
	 * @Description : This is wrapper method used for doubleClick on element
	 * @param : locator - By identification of element
	 * @return : - true if double click successful
	 * @author : Vikram Bendre (SQS)
	 */
	 public boolean doubleClick(String selector) {
	        try {
	            Locator locator = page.locator(selector);
	            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
	            locator.dblclick();
	            return true;
	        } catch (Exception e) {
	            WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(e, checkPageisLoadedCompletely());
	            e.printStackTrace();
	            return false;
	        }
	    }

	/**
	 * @Method : setText
	 * @Description : This is wrapper method to set text for input element
	 * @param : locator - By identification of element
	 * @param : fieldValue - field value as string
	 * @return : - true if text entered successfully
	 * @author : Vikram Bendre (SQS)
	 */
	 public boolean setText(String selector, String fieldValue) {
	        try {
	            Locator locator = page.locator(selector);
	            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
	            locator.fill(""); // Clear existing text
	            locator.type(fieldValue); // Type new value
	            return true;
	        } catch (Exception e) {
	            WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(e, checkPageisLoadedCompletely(), "true", fieldValue);
	            e.printStackTrace();
	            return false;
	        }
	    }

	/**
	 * @Method : getText
	 * @Description : This is wrapper method to get text form input elements
	 * @param : locator - By identification of element
	 * @param : textBy - get text by value attribute (set textBy as value)/ by
	 *        visible text (set textBy as text)
	 * @return : - text as string
	 * @author : Vikram Bendre (SQS)
	 */
	 public String getText(String selector, String textBy) {
	        try {
	            Locator locator = page.locator(selector);
	            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));

	            String strText = "";
	            if (textBy.equalsIgnoreCase("value")) {
	                strText = locator.getAttribute("value");
	            } else if (textBy.equalsIgnoreCase("text")) {
	                strText = locator.textContent();
	            }
	            return strText;
	        } catch (Exception e) {
	            WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(e, checkPageisLoadedCompletely());
	            e.printStackTrace();
	            return null;
	        }
	    }


	/**
	 * @Method : selectCheckBox
	 * @Description : This is wrapper method select/deselect checkbox
	 * @param : locator - By identification of element
	 * @param : status - select/deselect
	 * @author : Harshvardhan Yadav (SQS)
	 */
	 public boolean selectCheckBox(String selector, boolean status) {
	        try {
	            Locator locator = page.locator(selector);
	            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

	            String type = locator.getAttribute("type");
	            System.out.println(type);

	            if ("checkbox".equalsIgnoreCase(type)) {
	                boolean isChecked = locator.isChecked();
	                if ((isChecked && !status) || (!isChecked && status)) {
	                    locator.click();
	                }
	                return true;
	            } else {
	                return false;
	            }
	        } catch (Exception e) {
	            WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(e, checkPageisLoadedCompletely());
	            e.printStackTrace();
	            return false;
	        }
	    }

	/**
	 * @Method : isCheckBoxSelected
	 * @Description : This is wrapper checkbox is selected or not
	 * @param : locator - By identification of element
	 * @author :Harshvardhan Yadav (SQS)
	 */
	 public boolean isCheckBoxSelected(String selector) {
	        try {
	            Locator locator = page.locator(selector);
	            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));

	            String type = locator.getAttribute("type");
	            if ("checkbox".equalsIgnoreCase(type)) {
	                return locator.isChecked();
	            } else {
	                return false;
	            }
	        } catch (Exception e) {
	            WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(e, checkPageisLoadedCompletely());
	            e.printStackTrace();
	            return false;
	        }
	    }

	/**
	 * @Method : selectRadioButton
	 * @Description : This is wrapper method select/deselect radio button
	 * @param : locator - By identification of element
	 * @param : status - select/deselect
	 * @author : Indrajeet Chavan (SQS)
	 */
	 public boolean selectRadioButton(String selector, boolean status) {
	        try {
	            Locator locator = page.locator(selector);
	            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

	            String type = locator.getAttribute("type");
	            if ("radio".equalsIgnoreCase(type)) {
	                boolean isSelected = locator.isChecked();
	                if ((isSelected && !status) || (!isSelected && status)) {
	                    locator.click();
	                }
	                return true;
	            } else {
	                return false;
	            }
	        } catch (Exception e) {
	            WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(e, checkPageisLoadedCompletely());
	            e.printStackTrace();
	            return false;
	        }
	    }

	/**
	 * @Method : isRadioButtonSelected
	 * @Description : This is wrapper RadioButton is selected or not
	 * @param : locator - By identification of element
	 * @author : Indrajeet Chavan (SQS)
	 */
	 public boolean isRadioButtonSelected(String selector) {
	        try {
	            Locator locator = page.locator(selector);
	            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));

	            String type = locator.getAttribute("type");
	            if ("radio".equalsIgnoreCase(type)) {
	                return locator.isChecked();
	            } else {
	                return false;
	            }
	        } catch (Exception e) {
	            WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(e, checkPageisLoadedCompletely());
	            e.printStackTrace();
	            return false;
	        }
	    }

	/**
	 * @Method : mouseHover
	 * @Description : This is wrapper method used for Mouse Hovering to the element
	 * @param : locator - By identification of element
	 * @author : Indrajeet Chavan (SQS)
	 */
	 public boolean mouseHover(Locator locator) {
		    try {
		        locator.hover();
		        return true;
		    } catch (Exception exception) {
		        System.out.println(Arrays.toString(exception.getStackTrace()));
		        return false;
		    }
		}

	/**
	 * @Method : switchToWindowUsingTitle
	 * @Description : This is wrapper method used switch to window using the given
	 *              title
	 * @param : locator - Window title
	 * @author : Vikram Bendre (SQS)
	 */
	 public boolean switchToWindowUsingTitle(String windowTitle, BrowserContext context) {
		    try {
		        List<Page> pages = context.pages();

		        for (Page page : pages) {
		            if (page.title().equals(windowTitle)) {
		                page.bringToFront(); // Focus the desired page
		                return true;
		            }
		        }

		        return false;
		    } catch (Exception exception) {
		        WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(
		            exception, checkPageisLoadedCompletely(), "true", windowTitle
		        );
		        System.out.println(Arrays.toString(exception.getStackTrace()));
		        return false;
		    }
		}

	/**
	 * @Method : switchToFrameUsingIframe_Element
	 * @Description : This method will switch you to the Frame by Frame name
	 * @param : locator - The most common one. You locate your iframe like other
	 *        elements, then pass it into the method
	 *        eg.driver.switchTo().frame(driver.findElement(By.xpath(".//iframe[@title='Fill
	 *        Quote']")))
	 * @author : Vikram Bendre (SQS)
	 */
	 public FrameLocator switchToFrameUsingIframeElement(String iframeSelector) {
		    try {
		        // Create a FrameLocator for the iframe
		        FrameLocator frameLocator = page.frameLocator(iframeSelector);

		        // Optionally wait for the frame to be attached
		        frameLocator.locator("body").waitFor(); // Ensures frame is loaded

		        return frameLocator;
		    } catch (Exception exception) {
		        WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(
		            exception, checkPageisLoadedCompletely()
		        );
		        System.out.println(Arrays.toString(exception.getStackTrace()));
		        return null;
		    }
		}

	/**
	 * @Method : switchToFrameUsingNameOrId
	 * @Description : This method will switch you to the Frame by Frame name
	 * @param : frameName - Name/Id of frame you want to switch
	 * @author : Vikram Bendre (SQS)
	 */
	 public Frame switchToFrameUsingNameOrId(String frameName) {
		    try {
		        Frame targetFrame = page.frame(frameName);
		        if (targetFrame != null) {
		            return targetFrame;
		        } else {
		            System.out.println("Frame with name or ID '" + frameName + "' not found.");
		            return null;
		        }
		    } catch (Exception exception) {
		        WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(
		            exception, checkPageisLoadedCompletely()
		        );
		        System.out.println(Arrays.toString(exception.getStackTrace()));
		        return null;
		    }
		}
	/**
	 * @Method : switchToFrameUsingIndex
	 * @Description : This method will switch you to the Frame by Frame name
	 * @param : index - Index on page
	 * @author : Vikram Bendre (SQS)
	 */
	 public Frame switchToFrameUsingIndex(int index) {
		    try {
		        List<Frame> frames = page.frames();

		        if (index >= 0 && index < frames.size()) {
		            return frames.get(index);
		        } else {
		            System.out.println("Frame index out of bounds: " + index);
		            return null;
		        }
		    } catch (Exception exception) {
		        WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(
		            exception, checkPageisLoadedCompletely()
		        );
		        System.out.println(Arrays.toString(exception.getStackTrace()));
		        return null;
		    }
		}

	public boolean checkElementDisplyed(Locator locator) {
	    try {
	        return locator.isVisible();
	    } catch (Exception exception) {
	        WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(
	                exception, checkPageisLoadedCompletely());
	        return false;
	    }
	}
	// requires: import com.microsoft.playwright.Page;
	public String checkPageisLoadedCompletely() {
	    try {
	        // Check if document.readyState is 'complete'
	        Object readyObj = page.evaluate("() => document.readyState");
	        String readyState = readyObj == null ? "" : readyObj.toString();
	        if (!"complete".equals(readyState)) {
	            return "PAGE DID NOT LOAD COMPLETELY";
	        }

	        // Check for error keywords in title
	        String title = page.title();
	        if (title != null && (title.contains("ERROR") || title.contains("Error"))) {
	            return "APPLICATION THREW ERROR";
	        }

	        String pageContent = page.content();
	        if ((pageContent != null && pageContent.contains("This site can't be reached")) 
	                || (title != null && title.contains("."))) {
	            return "APPLICATION ERROR";
	        }
	    } catch (Exception e) {
	        return "PAGE VALIDATION FAILED: " + e.getMessage();
	    }

	    return null; // Page loaded successfully
	}
	public List<Locator> getWebElementList(String objLocator) {
	    // Equivalent to driver.findElements(By)
	    // Accepts an XPath or CSS selector string
	    return page.locator(objLocator).all();
	}
	
	public boolean click(Locator locator) {
	    try {
	        // Wait until the element is visible and enabled
	        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

	        // Scroll into view if needed (Playwright handles this automatically, but you can force it)
	        locator.scrollIntoViewIfNeeded();

	        // Click the element
	        locator.click();

	        return true;
	    } catch (Exception exception) {
	        WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(
	            exception, checkPageisLoadedCompletely()
	        );
	        return false;
	    }
	}
	public String getText(Locator locator, String textBy) {
	    try {
	        if (textBy.equalsIgnoreCase("value")) {
	            return locator.inputValue(); // For input fields
	        } else if (textBy.equalsIgnoreCase("text")) {
	            return locator.textContent(); // For visible text
	        } else {
	            System.out.println("Unsupported textBy option: " + textBy);
	            return null;
	        }
	    } catch (Exception exception) {
	        WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(
	            exception, checkPageisLoadedCompletely()
	        );
	        System.out.println(Arrays.toString(exception.getStackTrace()));
	        return null;
	    }
	}	
	public boolean setText(Locator locator, String fieldValue) {
	    try {
	        // Clear existing text and enter new value
	        locator.fill(fieldValue);
	        return true;
	    } catch (Exception exception) {
	        WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(
	            exception, checkPageisLoadedCompletely(), "true", fieldValue
	        );
	        System.out.println(Arrays.toString(exception.getStackTrace()));
	        return false;
	    }
	}
	public boolean waitTillElementEnabled(Locator webElement) {
	    try {
	        int iCount = 0;
	        do {
	            Thread.sleep(1000L);

	            if (this.checkElementIsEnabled(webElement)) {
	                return true;
	            }
	            iCount = iCount + 1;
	        } while (iCount < 30);
	        return false;
	    } catch (Exception exception) {
	        WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(exception,
	                checkPageisLoadedCompletely());
	        System.out.println(exception.getStackTrace());
	        return false;
	    }
	}
	public boolean checkElementIsEnabled(Locator webElement) {
	    try {
	        return webElement.isEnabled();
	    } catch (Exception exception) {
	        WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(
	                exception, checkPageisLoadedCompletely());
	        return false;
	    }
	}

	public boolean scrollTillElement(String objLocator) {
    try {
        Locator webElement = page.locator(objLocator).first();
        webElement.scrollIntoViewIfNeeded(); // same as JS scrollIntoView(false)
        return true;
    } catch (Exception exception) {
        WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(
                exception, checkPageisLoadedCompletely());
        System.out.println(exception.getStackTrace());
        return false;
    }
}
	public boolean switchToWindowByContainsTitle(String windowTitle) {
	    try {
	        Page mainWindowHandle = page; // current page reference

	        List<Page> openWindows = page.context().pages();
	        boolean status = false;

	        if (!openWindows.isEmpty()) {
	            for (Page windows : openWindows) {
	                String window = windows.title();

	                if (window != null && window.contains(windowTitle)) {
	                    // Switch context to this page
	                    this.page = windows;
	                    status = true;
	                    return true;
	                }
	            }

	            // If not found, switch back to main window
	            if (!status) {
	                this.page = mainWindowHandle;
	            }
	        }

	        return false;

	    } catch (Exception exception) {
	        WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(
	                exception, checkPageisLoadedCompletely(), "true", windowTitle);
	        System.out.println(exception.getStackTrace());
	        return false;
	    }
	}
	public int getTableRowCount(Locator objWETable) {
	    try {
	        List<Locator> objRows = objWETable.locator(".//td/parent::tr").all();
	        System.out.println(objRows.size());
	        return objRows.size();
	    } catch (Exception exception) {
	        WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(
	                exception, checkPageisLoadedCompletely());
	        return -1;
	    }
	}
	public String GetUserName() {
	    try {
	        Locator webElement = page.locator(
	            "//li[@class='dropdown user user-menu notifications-menu']//span"
	        );

	        // If first locator is not visible, try the fallback
	        if (!webElement.isVisible()) {
	            webElement = page.locator("//span[@class='username']");
	        }

	        return webElement.innerText();
	    } catch (Exception exception) {
	        WrapperFunctions.failureType =
	                exceptionHandler.analyzeFailureReason(exception, checkPageisLoadedCompletely());
	        // WrapperFunctions.failureType = "Xpath Change";
	        return null;
	    }
	}

	public boolean scrollTillElement(Locator locator) {
    try {
        // Exact equivalent of:
        // ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(false);", webElement);
        locator.evaluate("element => element.scrollIntoView(false)");
        return true;
    } catch (Exception exception) {
        WrapperFunctions.failureType =
                exceptionHandler.analyzeFailureReason(exception, checkPageisLoadedCompletely());
        System.out.println(exception.getStackTrace());
        // WrapperFunctions.failureType = "Require Analysis";
        return false;
    }
}
	public boolean isCheckBoxSelected(Locator locator) {
	    boolean state = false;
	    try {
	        String type = locator.getAttribute("type");

	        if ("checkbox".equalsIgnoreCase(type)) {
	            state = locator.isChecked();
	        }

	        return state;
	    } catch (Exception exception) {
	        WrapperFunctions.failureType =
	                exceptionHandler.analyzeFailureReason(exception, checkPageisLoadedCompletely());
	        System.out.println(exception.getStackTrace());
	        return false;
	    }
	}

	public boolean isCheckBoxSelected(Locator sCheckedValue, boolean status) {

	    // Equivalent of waitForElementPresence(locator)
		sCheckedValue.waitFor(new Locator.WaitForOptions()
		        .setState(WaitForSelectorState.ATTACHED));

	    boolean state = false;

	    try {
	        Locator webElement = sCheckedValue;
	        String type = webElement.getAttribute("type");

	        if ("checkbox".equalsIgnoreCase(type)) {
	            state = webElement.isChecked();
	        }

	        return state;
	    } catch (Exception exception) {
	        WrapperFunctions.failureType =
	                exceptionHandler.analyzeFailureReason(exception, checkPageisLoadedCompletely());
	        System.out.println(exception.getStackTrace());
	        // WrapperFunctions.failureType = "Xpath Change";
	        return false;
	    }
	}
	public boolean ActionKeyBoardOperations(String KeysTopress) {
	    try {
	        Keyboard keyboard = page.keyboard();

	        KeysTopress = KeysTopress.toLowerCase();
	        System.out.println("Key Name =" + KeysTopress);

	        switch (KeysTopress) {
	            case "downkey":
	                keyboard.press("ArrowDown");
	                break;

	            case "enter":
	                keyboard.press("Enter");
	                break;

	            case "tab":
	                keyboard.press("Tab");
	                break;

	            case "return":
	                keyboard.press("Enter");
	                break;

	            case "escape":
	                keyboard.press("Escape");
	                break;

	            case "backspace":
	                keyboard.press("Backspace");
	                break;

	            case "upkey":
	                keyboard.press("ArrowUp");
	                break;

	            default:
	                // Send literal text as key input
	                keyboard.insertText(KeysTopress);
	                break;
	        }

	        return true;
	    } catch (Exception exception) {
	        WrapperFunctions.failureType =
	                exceptionHandler.analyzeFailureReason(exception, checkPageisLoadedCompletely());
	        System.out.println(exception.getStackTrace());
	        return false;
	    }
	}
	public boolean setTextCharByChar(Locator locator, String sText) {
	    boolean bResult = false;

	    try {
	        for (int iCount = 0; iCount < sText.length(); iCount++) {
	            String singleChar = Character.toString(sText.charAt(iCount));

	            locator.type(singleChar);
	            bResult = true;

	            Thread.sleep(TimeUnit.MILLISECONDS.convert(5, TimeUnit.SECONDS));
	        }

	        return bResult;
	    } catch (Exception exception) {
	        WrapperFunctions.failureType =
	                exceptionHandler.analyzeFailureReason(exception,
	                        checkPageisLoadedCompletely(), "true", sText);
	        return false;
	    }
	}
	
	public boolean selectDropDownOption(Locator dropdown, String option, String... selectType) {
	    try {
	        // ✅ Scroll into view (Playwright native replacement for JS scrollIntoView)
	        dropdown.scrollIntoViewIfNeeded();

	        // ✅ If selectType is provided (Value / Text / Index)
	        if (selectType != null && selectType.length > 0 
	                && selectType[0] != null && !selectType[0].isEmpty()) {

	            if (selectType[0].equalsIgnoreCase("Value")) {
	                dropdown.selectOption(new SelectOption().setValue(option));
	            } 
	            else if (selectType[0].equalsIgnoreCase("Text")) {
	                dropdown.selectOption(new SelectOption().setLabel(option));
	            } 
	            else if (selectType[0].equalsIgnoreCase("Index")) {
	                dropdown.selectOption(new SelectOption().setIndex(Integer.parseInt(option)));
	            }

	            return true;
	        } 
	        else {
	            // ✅ Fallback logic: loop through options and select by matching text
	            Locator options = dropdown.locator("option");
	            int count = options.count();
	            boolean blnOptionAvailable = false;

	            for (int iIndex = 0; iIndex < count; iIndex++) {
	                String text = options.nth(iIndex).innerText().trim();
	                if (text.equals(option)) {
	                    dropdown.selectOption(new SelectOption().setIndex(iIndex));
	                    blnOptionAvailable = true;
	                    break;
	                }
	            }
	            return blnOptionAvailable;
	        }

	    } catch (Exception exception) {
	        WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(
	                exception, checkPageisLoadedCompletely(), "true", option);
	        System.out.println(exception.getStackTrace());
	        return false;
	    }
	}
	public boolean selectCheckBox(Locator locator, boolean status) {
	    try {
	        // Ensure it's a checkbox
	        String type = locator.getAttribute("type");
	        if ("checkbox".equalsIgnoreCase(type)) {

	            boolean isChecked = locator.isChecked();

	            // ✅ Original logic preserved:
	            // If checked and status=false → uncheck
	            // If unchecked and status=true → check
	            if ((isChecked && !status) || (!isChecked && status)) {
	                locator.click();
	            }
	            return true;

	        } else {
	            return false;
	        }
	    } catch (Exception exception) {
	        WrapperFunctions.failureType =
	                exceptionHandler.analyzeFailureReason(exception, checkPageisLoadedCompletely());
	        System.out.println(exception.getStackTrace());
	        return false;
	    }
	}
	public String getAttributeValue(Locator locator, String attributeName) {
	    try {
	        // ✅ Wait until the element is attached (equivalent of waitForElementPresence)
	        locator.waitFor(new Locator.WaitForOptions()
	                .setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));

	        return locator.getAttribute(attributeName);

	    } catch (Exception exception) {
	        WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(
	                exception, checkPageisLoadedCompletely(), "true", attributeName);
	        // WrapperFunctions.failureType = "Locator Property Change";
	        return null;
	    }
	}

	public String getAttributeValue(String selector, String attributeName) {
	    try {
	        // ✅ Equivalent of waitForElementPresence(locator)
	        page.waitForSelector(selector,
	                new com.microsoft.playwright.Page.WaitForSelectorOptions()
	                        .setState(WaitForSelectorState.ATTACHED));

	        Locator webElement = page.locator(selector);
	        return webElement.getAttribute(attributeName);

	    } catch (Exception exception) {
	        WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(
	                exception, checkPageisLoadedCompletely(), "true", attributeName);
	        // WrapperFunctions.failureType = "Locator Property Change";
	        return null;
	    }
	}
	public Locator getWebElement(String selector) {
	    try {
	        // ✅ Equivalent of waitForElementPresence(By)
	        page.waitForSelector(selector,
	                new com.microsoft.playwright.Page.WaitForSelectorOptions()
	                        .setState(WaitForSelectorState.ATTACHED));

	        return page.locator(selector);

	    } catch (Exception e) {
	        WrapperFunctions.failureType =
	                exceptionHandler.analyzeFailureReason(e, checkPageisLoadedCompletely());
	        System.out.println(e.getStackTrace());
	        return null;
	    }
	}
	
	public boolean javaScriptClick(Locator locator) {
	    try {
	        // ✅ Equivalent of waitTillElementPresent(WebElement)
	        locator.waitFor(new Locator.WaitForOptions()
	                .setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));

	        // ✅ Exact Playwright equivalent of JS click
	        locator.evaluate("element => element.click()");

	        return true;
	    } catch (Exception exception) {
	        WrapperFunctions.failureType =
	                exceptionHandler.analyzeFailureReason(exception, checkPageisLoadedCompletely());
	        System.out.println(exception.getStackTrace());
	        // WrapperFunctions.failureType = "Require Analysis";
	        return false;
	    }
	}
	
	public boolean selectDropDownOption(String selector, String option, String... selectType) {
	    try {
	        // waitForElementPresence(locator) equivalent
	        page.waitForSelector(selector);

	        Locator dropdown = page.locator(selector);

	        // scrollIntoView(false)
	        dropdown.scrollIntoViewIfNeeded();

	        // If selectType passed: Value / Text / Index
	        if (selectType.length > 0 && selectType[0] != null && !selectType[0].isEmpty()) {
	            String type = selectType[0];

	            if (type.equalsIgnoreCase("Value")) {
	                dropdown.selectOption(new SelectOption().setValue(option));
	            } else if (type.equalsIgnoreCase("Text")) {
	                dropdown.selectOption(new SelectOption().setLabel(option));
	            } else if (type.equalsIgnoreCase("Index")) {
	                dropdown.selectOption(new SelectOption().setIndex(Integer.parseInt(option)));
	            }

	            return true;
	        } else {
	            // Fallback: loop options, match by text, select by index
	            Locator options = dropdown.locator("option");
	            int count = options.count();
	            boolean blnOptionAvailable = false;

	            for (int iIndex = 0; iIndex < count; iIndex++) {
	                String text = options.nth(iIndex).innerText().trim();
	                if (text.equals(option)) {
	                    dropdown.selectOption(new SelectOption().setIndex(iIndex));
	                    blnOptionAvailable = true;
	                    break;
	                }
	            }
	            return blnOptionAvailable;
	        }
	    } catch (Exception exception) {
	        WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(
	                exception, checkPageisLoadedCompletely(), "true", option);
	        System.out.println(exception.getStackTrace());
	        return false;
	    }
	}
	public int getTableColumn(Locator objWETable, String sColumnName) {
	    try {
	        // All header cells in first row of the table
	        Locator objColumns = objWETable.locator("xpath=.//tr[1]/th");
	        int count = objColumns.count();
	        int iCount = 0;

	        for (int i = 0; i < count; i++) {
	            Locator objCol = objColumns.nth(i);

	            if (sColumnName.equals("")) {
	                // Scroll first header into view and return 0 (same as original)
	                objCol.scrollIntoViewIfNeeded();
	                return 0;
	            }

	            objCol.scrollIntoViewIfNeeded();

	            String text = objCol.innerText();
	            if (text != null) {
	                // If you already converted scrollTillElement to Locator, call it here:
	                // this.scrollTillElement(objCol);
	                Thread.sleep(1000L);

	                System.out.println(text);
	                System.out.println(text.contains(sColumnName));

	                if (text.contains(sColumnName)) {
	                    return iCount + 1;
	                }
	            }

	            iCount = iCount + 1;
	        }

	        return 0;
	    } catch (Exception exception) {
	        return 0;
	    }
	}
	
	public int getTableRow(Locator objWETable, int iColNum, String sTableValue) {
	    try {
	        // Equivalent of:
	        // objWETable.findElements(By.xpath(".//td[" + iColNum + "]"))
	        Locator objRows = objWETable.locator("xpath=.//td[" + iColNum + "]");
	        int rowCount = objRows.count();

	        for (int iCount = 0; iCount < rowCount; iCount++) {
	            System.out.println(iCount);

	            Locator currentCell = objRows.nth(iCount);

	            System.out.println(rowCount + " " + currentCell);

	            String cellText = currentCell.innerText();
	            System.out.println(cellText);

	            // If empty, scroll into view (true)
	            if (cellText.equals("")) {
	                currentCell.evaluate("el => el.scrollIntoView(true)");
	            }

	            // If matching value found
	            if (cellText.contains(sTableValue)) {
	                currentCell.evaluate("el => el.scrollIntoView(false)");
	                iCount = iCount + 1;  // Selenium logic preserved (1-based index)
	                return iCount;
	            }
	        }

	        return 0;
	    } catch (Exception exception) {
	        return 0;
	    }
	}
	
	public Locator getWebElementBasedOnActionInTableCell(
	        Locator objWETable, int iRow, int iColumn, String sControl) {

	    try {
	        // Equivalent of:
	        // objWETable.findElement(By.xpath(".//tr[" + iRow + "]//td[" + iColumn + "]"))
	        Locator objTableCell = objWETable.locator(
	                "xpath=.//tr[" + iRow + "]//td[" + iColumn + "]");

	        // Equivalent of scrollTillElement(WebElement)
	        objTableCell.evaluate("el => el.scrollIntoView(false)");

	        switch (sControl.toLowerCase()) {
	            case "button":
	                // .//a
	                return objTableCell.locator("xpath=.//a");

	            case "dropdown":
	                // .//select
	                return objTableCell.locator("xpath=.//select");

	            case "textbox":
	            case "checkbox":
	                // .//input
	                return objTableCell.locator("xpath=.//input");

	            default:
	                return objTableCell;
	        }

	    } catch (Exception exception) {
	        WrapperFunctions.failureType =
	                exceptionHandler.analyzeFailureReason(exception,
	                        checkPageisLoadedCompletely(), "true", sControl);
	        // WrapperFunctions.failureType = "Xpath Change";
	        return null;
	    }
	}
	
	public boolean waitTillElementGetDisAppeared(Locator locator) {
	    try {
	        int i = 0;

	        do {
	            Thread.sleep(1000L); // same 1-second wait
	            i = i + 1;

	            if (i == 60) {  // same 60-second max wait
	                System.out.println("Element is present...." + locator);
	                break;
	            }

	        } while (locator.isVisible());  // equivalent of checkElementDisplyed(webElement)

	        return true;

	    } catch (Exception exception) {
	        WrapperFunctions.failureType =
	                exceptionHandler.analyzeFailureReason(exception, checkPageisLoadedCompletely());
	        System.out.println(exception.getStackTrace());
	        return false;
	    }
	}
	
	public boolean scrollAndclick(String selector) {
	    // Equivalent of waitForElementPresence(locator)
	    page.waitForSelector(selector);

	    try {
	        Locator webElement = page.locator(selector);

	        // Repeat wait as in your Selenium logic
	        page.waitForSelector(selector);

	        // Equivalent of: executeScript("arguments[0].scrollIntoView(false);", webElement)
	        webElement.evaluate("el => el.scrollIntoView(false)");

	        // Repeat wait again
	        page.waitForSelector(selector);

	        // Equivalent of: window.scrollTo(0, -document.body.scrollHeight)
	        page.evaluate("() => window.scrollTo(0, -document.body.scrollHeight)");

	        // Click the element
	        webElement.click();

	        return true;

	    } catch (Exception exception) {
	        WrapperFunctions.failureType =
	                exceptionHandler.analyzeFailureReason(exception, checkPageisLoadedCompletely());
	        System.out.println(exception.getStackTrace());
	        // WrapperFunctions.failureType = "Require Analysis";
	        return false;
	    }
	}
	public boolean setTextCharByChar(String selector, String sText) {
	    boolean bResult = false;

	    try {
	        // Equivalent of waitForElementPresence(objLocator)
	        page.waitForSelector(selector);

	        Locator locator = page.locator(selector);

	        for (int iCount = 0; iCount < sText.length(); iCount++) {
	            String singleChar = Character.toString(sText.charAt(iCount));

	            // Playwright equivalent of sendKeys(char)
	            locator.type(singleChar);
	            bResult = true;

	            Thread.sleep(TimeUnit.MILLISECONDS.convert(5, TimeUnit.SECONDS));
	        }

	        return bResult;
	    } catch (Exception exception) {
	        WrapperFunctions.failureType =
	                exceptionHandler.analyzeFailureReason(exception,
	                        checkPageisLoadedCompletely(), "true", sText);
	        return false;
	    }
	}
	public boolean clearText(String selector) {
	    // Equivalent of waitForElementPresence(objLocator)
	    page.waitForSelector(selector);

	    try {
	        Locator webElement = page.locator(selector);

	        // Playwright equivalent of webElement.clear()
	        webElement.clear();

	        return true;

	    } catch (Exception exception) {
	        WrapperFunctions.failureType =
	                exceptionHandler.analyzeFailureReason(exception, checkPageisLoadedCompletely());
	        System.out.println(exception.getStackTrace());
	        // WrapperFunctions.failureType = "Xpath Change";
	        return false;
	    }
	}
	public boolean isCheckBoxSelected(String selector, boolean status) {
	    // Equivalent of waitForElementPresence(locator)
	    page.waitForSelector(selector);

	    boolean state = false;

	    try {
	        Locator webElement = page.locator(selector);

	        String type = webElement.getAttribute("type");
	        if ("checkbox".equalsIgnoreCase(type)) {
	            // Playwright equivalent of isSelected()
	            state = webElement.isChecked();
	        }

	        return state;

	    } catch (Exception exception) {
	        WrapperFunctions.failureType =
	                exceptionHandler.analyzeFailureReason(exception, checkPageisLoadedCompletely());
	        System.out.println(exception.getStackTrace());
	        // WrapperFunctions.failureType = "Xpath Change";
	        return false;
	    }
	}
	public void waitFormobileElementToBeClickable(String selector) {
	    try {
	        // ✅ Wait until element is visible (closest equivalent to "clickable")
	        page.waitForSelector(selector,
	                new com.microsoft.playwright.Page.WaitForSelectorOptions()
	                        .setState(WaitForSelectorState.VISIBLE));

	        // ✅ Ensure element is enabled (extra safety for mobile)
	        Locator element = page.locator(selector);
	        element.waitFor(new Locator.WaitForOptions()
	                .setState(WaitForSelectorState.VISIBLE));

	    } catch (Exception exception) {
	        System.out.println(exception.getStackTrace());
	    }
	}
	public boolean sendKeys(String selector, String sValue) {

	    try {
	        // ✅ Equivalent of waitForElementPresence(objLocator)
	        page.waitForSelector(selector);

	        Locator objWE = page.locator(selector);

	        // ✅ Correct replacement for deprecated locator.type()
	        objWE.pressSequentially(sValue);

	        return true;

	    } catch (Exception exception) {
	        WrapperFunctions.failureType =
	                exceptionHandler.analyzeFailureReason(exception,
	                        checkPageisLoadedCompletely(), "true", sValue);
	        System.out.println(exception.getStackTrace());
	        return false;
	    }
	}
	
	public boolean ValidateAndHandleAlert(String strText) {
	    try {
	        final boolean[] isMatched = { false };

	        // ✅ Playwright handles alerts via a dialog listener
	        page.onceDialog(dialog -> {
	            String alertText = dialog.message();

	            if (alertText != null && alertText.contains(strText)) {
	                dialog.accept();   // ✅ Equivalent of alert.accept()
	                isMatched[0] = true;
	            } else {
	                dialog.dismiss();  // ✅ Safer fallback
	                isMatched[0] = false;
	            }
	        });

	        return isMatched[0];

	    } catch (Exception exception) {
	        WrapperFunctions.failureType =
	                exceptionHandler.analyzeFailureReason(exception,
	                        checkPageisLoadedCompletely(), "true", strText);
	        System.out.println(exception.getStackTrace());
	        // WrapperFunctions.failureType = "Xpath Change";
	        return false;
	    }
	}
	public int getTableRowWithExactname(Locator objWETable, int iColNum, String sTableValue) {
	    try {
	        // Equivalent of: objWETable.findElements(By.xpath(".//td[" + iColNum + "]"))
	        Locator objRows = objWETable.locator("xpath=.//td[" + iColNum + "]");
	        int rowCount = objRows.count();

	        for (int iCount = 0; iCount < rowCount; iCount++) {
	            Locator currentCell = objRows.nth(iCount);

	            String cellText = currentCell.innerText();
	            System.out.println(cellText);

	            if (cellText.equals("")) {
	                // scrollIntoView(true)
	                currentCell.evaluate("el => el.scrollIntoView(true)");
	            }

	            if (cellText.contentEquals(sTableValue)) {
	                // scrollIntoView(false)
	                currentCell.evaluate("el => el.scrollIntoView(false)");
	                iCount = iCount + 1; // keep 1-based index behavior
	                return iCount;
	            }
	        }
	        return 0;
	    } catch (Exception exception) {
	        WrapperFunctions.failureType =
	                exceptionHandler.analyzeFailureReason(exception,
	                        checkPageisLoadedCompletely(), "true", sTableValue);
	        // WrapperFunctions.failureType = "Xpath Change";
	        return 0;
	    }
	}
	public int getTableRowWithExactValue(Locator objWETable, int iColNum, String sTableValue) {
	    try {
	        // Equivalent of:
	        // objWETable.findElements(By.xpath(".//td[" + iColNum + "]"))
	        Locator objRows = objWETable.locator("xpath=.//td[" + iColNum + "]");
	        int rowCount = objRows.count();

	        for (int iCount = 0; iCount < rowCount; iCount++) {
	            Locator currentCell = objRows.nth(iCount);

	            String cellText = currentCell.innerText();
	            System.out.println(cellText);

	            // If empty, scroll into view (true)
	            if (cellText.equals("")) {
	                currentCell.evaluate("el => el.scrollIntoView(true)");
	            }

	            // Exact match check
	            if (cellText.equals(sTableValue)) {
	                currentCell.evaluate("el => el.scrollIntoView(false)");
	                iCount = iCount + 1;   // keep 1-based index behavior
	                return iCount;
	            }
	        }

	        return 0;
	    } catch (Exception exception) {
	        WrapperFunctions.failureType =
	                exceptionHandler.analyzeFailureReason(exception,
	                        checkPageisLoadedCompletely(), "true", sTableValue);
	        // WrapperFunctions.failureType = "Xpath Change";
	        return 0;
	    }
	}
	public boolean waitForElementPresence(Locator selector) {
	    try {
	        // ✅ Equivalent of ExpectedConditions.presenceOfElementLocated(locator)
	    	selector.waitFor(new Locator.WaitForOptions()
	    	        .setState(WaitForSelectorState.ATTACHED));

	        return true;

	    } catch (Exception exception) {
	        WrapperFunctions.failureType =
	                exceptionHandler.analyzeFailureReason(exception,
	                        checkPageisLoadedCompletely());
	        return false;
	    }
	}
	public boolean selectDropDownOptionByContains(Locator dropdown, String option) {
	    try {
	        // ✅ Equivalent of waitForElementPresence(locator)
	        dropdown.waitFor(new Locator.WaitForOptions()
	                .setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED));

	        // ✅ Equivalent of scrollIntoView(false)
	        dropdown.scrollIntoViewIfNeeded();

	        // ✅ Get all <option> elements
	        Locator options = dropdown.locator("option");
	        int count = options.count();

	        boolean blnOptionAvailable = false;
	        int iIndex = 0;

	        for (int i = 0; i < count; i++) {
	            String text = options.nth(i).innerText().trim();

	            if (text.contains(option)) {
	                dropdown.selectOption(new SelectOption().setIndex(iIndex));
	                blnOptionAvailable = true;
	                break;
	            } else {
	                iIndex++;
	            }
	        }

	        return blnOptionAvailable;

	    } catch (Exception exception) {
	        WrapperFunctions.failureType =
	                exceptionHandler.analyzeFailureReason(
	                        exception, checkPageisLoadedCompletely(), "true", option);
	        System.out.println(exception.getStackTrace());
	        // WrapperFunctions.failureType = "Test Data";
	        return false;
	    }
	}
	public int getTableColumnWithExactName(Locator objWETable, String sColumnName) {
	    try {
	        // Equivalent of:
	        // objWETable.findElements(By.xpath(".//tr[1]/th"))
	        Locator objColumns = objWETable.locator("xpath=.//tr[1]/th");
	        int columnCount = objColumns.count();

	        int iCount = 0;

	        for (int i = 0; i < columnCount; i++) {
	            Locator objCol = objColumns.nth(i);

	            if (sColumnName.equals("")) {
	                // scrollIntoView(true)
	                objCol.evaluate("el => el.scrollIntoView(true)");
	                return 0;
	            }

	            // scrollIntoView(true)
	            objCol.evaluate("el => el.scrollIntoView(true)");

	            String colText = objCol.innerText();

	            if (colText != null) {
	                System.out.println(colText);
	                System.out.println(colText.equalsIgnoreCase(sColumnName));

	                if (colText.equalsIgnoreCase(sColumnName)) {
	                    return iCount + 1; // keep 1-based index behavior
	                }
	            }

	            iCount = iCount + 1;
	        }

	        return 0;

	    } catch (Exception exception) {
	        WrapperFunctions.failureType =
	                exceptionHandler.analyzeFailureReason(exception,
	                        checkPageisLoadedCompletely(), "true", sColumnName);
	        // WrapperFunctions.failureType = "Xpath Change";
	        return 0;
	    }
	}
	public boolean verifyDropDownOptionValues(Locator dropdown, String option) {
	    try {
	        // Get all <option> elements from the dropdown
	        Locator options = dropdown.locator("option");
	        int count = options.count();

	        boolean blnOptionAvailable = false;
	        java.util.ArrayList<String> optionsList;

	        // Build expected options list from "A;B;C" or single "A"
	        if (option.contains(";")) {
	            optionsList = new java.util.ArrayList<>(
	                    java.util.Arrays.asList(option.trim().split(";")));
	        } else {
	            optionsList = new java.util.ArrayList<>();
	            optionsList.add(option);
	        }

	        for (int i = 0; i < count; i++) {
	            String optionValue = options.nth(i).innerText().trim();

	            if (optionsList.contains(optionValue)) {
	                blnOptionAvailable = true;
	                optionsList.remove(optionValue);

	                if (optionsList.isEmpty()) {
	                    break;
	                }
	            }
	        }

	        return blnOptionAvailable;

	    } catch (Exception exception) {
	        WrapperFunctions.failureType =
	                exceptionHandler.analyzeFailureReason(
	                        exception, checkPageisLoadedCompletely(), "true", option);
	        System.out.println(exception.getStackTrace());
	        // WrapperFunctions.failureType = "Test Data";
	        return false;
	    }
	}
}
