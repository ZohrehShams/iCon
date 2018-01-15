package icircles.concreteDiagram;

/*
 * @author Jean Flower <jeanflower@rocketmail.com>
 * Copyright (c) 2012
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are
 * those of the authors and should not be interpreted as representing official
 * policies, either expressed or implied, of the iCircles Project.
 */

import icircles.abstractDescription.AbstractSpider;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.util.ArrayList;

public class ConcreteSpider {

    static Logger logger = Logger.getLogger(ConcreteSpider.class.getName());

    // TODO: make some data private
    public int footRad = 1;
    public ArrayList<ConcreteSpiderFoot> feet;
    public ArrayList<ConcreteSpiderLeg> legs;
    public AbstractSpider as;

    public ConcreteSpider(AbstractSpider as) {
        this.as = as;
        feet = new ArrayList<ConcreteSpiderFoot>();
        legs = new ArrayList<ConcreteSpiderLeg>();
    }

    public double checksum() {
        double result = 0.0;
        for (ConcreteSpiderFoot foot : feet) {
            logger.debug("build checksum for foot\n");

            result += foot.checksum();
        }
        for (ConcreteSpiderLeg leg : legs) {
            logger.debug("build checksum for leg\n");

            result += leg.checksum();
        }
        // TODO Auto-generated method stub
        return result;
    }
    
    
    //Zohreh:
    public AbstractSpider get_as(){
    	return as;
    }
    
    public ArrayList<ConcreteSpiderFoot> get_feet(){
    	return feet;
    }
    
    
    
}
