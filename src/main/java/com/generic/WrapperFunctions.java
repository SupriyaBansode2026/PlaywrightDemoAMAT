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

	public boolean waitForElementPresence(String selector, String sWaitFor) {
	    try {
	        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
	                .setTimeout(Long.parseLong(sWaitFor) * 1000)
	                .setState(WaitForSelectorState.ATTACHED)); // presence in DOM
	        return true;
	    } catch (PlaywrightException exception) {
	        WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(
	                exception, checkPageisLoadedCompletely());
	        return false;
	    }
	}
	
	public boolean waitForElementPresence(Locator locator, String sWaitFor) {
	    try {
	        locator.waitFor(new Locator.WaitForOptions()
	                .setTimeout(Long.parseLong(sWaitFor) * 1000)
	                .setState(WaitForSelectorState.ATTACHED)); // element present in DOM
	        return true;
	    } catch (PlaywrightException exception) {
	        WrapperFunctions.failureType = exceptionHandler.analyzeFailureReason(
	                exception, checkPageisLoadedCompletely());
	        return false;
	    }
	}
}
