/*
 * The MIT License
 * 
 * Copyright (c) 2011, Jesse Farinacci
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.jenkins.ci.plugins.xframe_filter;

import hudson.Extension;
import hudson.model.PageDecorator;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

/**
 * The <a
 * href="http://wiki.jenkins-ci.org/display/JENKINS/XFrame+Filter+Plugin">XFrame
 * Filter Plugin</a> provides a very simple filter which optionally adds an
 * X-Frame-Options response header. The <a
 * href="https://developer.mozilla.org/en/The_X-FRAME-OPTIONS_response_header"
 * >X-Frame-Options HTTP response header</a> can be used to indicate whether or
 * not a browser should be allowed to render a page in a &lt;frame&gt; or
 * &lt;iframe&gt;. Sites can use this to avoid clickjacking attacks, by ensuring
 * that their content is not embedded into other sites.
 * 
 * @author <a href="mailto:jieryn@gmail.com">Jesse Farinacci</a>
 */
@Extension
public final class XFrameFilterPageDecorator extends PageDecorator
{
  /**
   * TODO: make the global.jelly use a drop down with these two options only,
   * this is just too hard for me to figure out right now so I use a j.l.String.
   * Aaargh.
   * 
   * There are two possible values for X-Frame-Options:
   */
  enum Options
  {
    /**
     * The page cannot be displayed in a frame, regardless of the site
     * attempting to do so.
     */
    DENY,

    /**
     * The page can only be displayed in a frame on the same origin as the page
     * itself.
     */
    SAMEORIGIN
  };

  /**
   * The default value for {@link #sendHeader}.
   */
  protected static final boolean DEFAULT_SEND_HEADER = true;

  /**
   * The default value for {@link #options}.
   */
  // protected static final Options DEFAULT_OPTIONS = Options.SAMEORIGIN;
  protected static final String  DEFAULT_OPTIONS     = "SAMEORIGIN";

  /**
   * Whether or not to send the X-Frame-Options HTTP header.
   */
  private boolean                sendHeader;

  /**
   * There are two possible values for X-Frame-Options: DENY - the page cannot
   * be displayed in a frame, regardless of the site attempting to do so;
   * SAMEORIGIN - the page can only be displayed in a frame on the same origin
   * as the page itself.
   */
  private String                 options;

  /**
   * Create a default HSTS Filter {@link PageDecorator}.
   */
  public XFrameFilterPageDecorator()
  {
    this(DEFAULT_SEND_HEADER, DEFAULT_OPTIONS);
  }

  /**
   * Create a HSTS Filter {@link PageDecorator} with the specified
   * configuration.
   */
  @DataBoundConstructor
  public XFrameFilterPageDecorator(final boolean sendHeader,
      final String options)
  {
    super();
    load();
    this.sendHeader = sendHeader;
    this.options = options;
  }

  @Override
  public String getDisplayName()
  {
    return Messages.XFrame_Filter_Plugin_DisplayName();
  }

  @Override
  public boolean configure(final StaplerRequest request, final JSONObject json)
      throws FormException
  {
    request.bindJSON(this, json);
    save();
    return true;
  }

  public boolean isSendHeader()
  {
    return sendHeader;
  }

  public void setSendHeader(final boolean sendHeader)
  {
    this.sendHeader = sendHeader;
  }

  public String getOptions()
  {
    return options;
  }

  public void setOptions(final String options)
  {
    this.options = options;
  }
}
