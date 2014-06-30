/*
 * The MIT License
 *
 * Copyright 2014 Jesse Glick.
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

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.jvnet.hudson.test.Bug;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.recipes.LocalData;

public class XFrameFilterPageDecoratorTest {

    @Rule public JenkinsRule r = new JenkinsRule();

    @Bug(22430)
    @LocalData
    // TODO prior to 1.572 this does not reproduce the bug properly since JenkinsRule.before was calling XFrameFilterPageDecorator.load
    @Test public void configuration() throws Exception {
        assertEquals("ALLOW-FROM http://nowhere.net/", r.createWebClient().goTo("").getWebResponse().getResponseHeaderValue("X-Frame-Options"));
        HtmlForm form = r.createWebClient().goTo("configure").getFormByName("config");
        form.getInputByName("_.options").setValueAttribute("ALLOW-FROM http://my.com/");
        r.submit(form);
        assertEquals("ALLOW-FROM http://my.com/", r.createWebClient().goTo("").getWebResponse().getResponseHeaderValue("X-Frame-Options"));
    }

}