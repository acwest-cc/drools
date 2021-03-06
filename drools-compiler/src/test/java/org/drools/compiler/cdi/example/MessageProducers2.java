/*
 * Copyright 2015 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.drools.compiler.cdi.example;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

public class MessageProducers2 {        
    @Inject @Msg1
    private String msg1;
    
    @Produces @Msg("chained1") 
    public String getChained1() {
        return "chained.1 " + msg1;
    }    
    
    @Produces @Msg("chained2") 
    public String getChained2(Message m1, @Msg1 String m2, @Msg("named1") String m3) {
        return "chained.2 " + m1.getText() + " " + m2 + " " + m3;
    }      
}
