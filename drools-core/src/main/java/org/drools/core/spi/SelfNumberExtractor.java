/*
 * Copyright 2005 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.core.spi;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.drools.core.base.ClassObjectType;
import org.drools.core.base.extractors.BaseNumberClassFieldReader;
import org.drools.core.common.InternalWorkingMemory;
import org.drools.core.util.ClassUtils;
import org.drools.facttemplates.Fact;

public class SelfNumberExtractor extends BaseNumberClassFieldReader
    implements
    InternalReadAccessor,
    AcceptsClassObjectType,
    Externalizable {

    private static final long serialVersionUID = 510l;
    private ObjectType        objectType;

    public SelfNumberExtractor() {
    }

    public SelfNumberExtractor(final ObjectType objectType) {
        super(-1, ((ClassObjectType) objectType).getClassType(), objectType.getValueType() );
    }

    public void readExternal(ObjectInput in) throws IOException,
                                            ClassNotFoundException {
        objectType = (ObjectType) in.readObject();
        setIndex( -1 );
        setFieldType( ((ClassObjectType) objectType).getClassType() );
        setValueType( objectType.getValueType() );
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject( objectType );
    }
    
    public void setClassObjectType(ClassObjectType objectType) {
        this.objectType = objectType;
        setIndex( -1 );
        setFieldType( ((ClassObjectType) objectType).getClassType() );
        setValueType( objectType.getValueType() );        
    }

    public Object getValue(InternalWorkingMemory workingMemory,
                           final Object object) {
        return object;
    }

    public ObjectType getObjectType() {
        return this.objectType;
    }

    public Class<?> getExtractToClass() {
        // @todo : this is a bit nasty, but does the trick
        if ( this.objectType instanceof ClassObjectType ) {
            return ((ClassObjectType) this.objectType).getClassType();
        } else {
            return Fact.class;
        }
    }

    public String getExtractToClassName() {
        Class<?> clazz = null;
        // @todo : this is a bit nasty, but does the trick
        if ( this.objectType instanceof ClassObjectType ) {
            clazz = ((ClassObjectType) this.objectType).getClassType();
        } else {
            clazz = Fact.class;
        }
        return ClassUtils.canonicalName( clazz );
    }

    public int hashCode() {
        return this.objectType.hashCode();
    }

    public boolean equals(final Object obj) {
        if ( this == obj ) {
            return true;
        }
        if ( !(obj instanceof SelfNumberExtractor) ) {
            return false;
        }
        final SelfNumberExtractor other = (SelfNumberExtractor) obj;
        return this.objectType.equals( other.objectType );
    }

    public boolean isGlobal() {
        return false;
    }

    public boolean isSelfReference() {
        return true;
    }
}