/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2011-2016, Telestax Inc and individual contributors
 * by the @authors tag.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jdiameter.server.impl.app.slh;

import org.jdiameter.api.InternalException;
import org.jdiameter.api.app.AppEvent;
import org.jdiameter.api.app.StateEvent;

/**
 * @author fernando.mendioroz@telestax.com (Fernando Mendioroz)
 *
 */

public class Event implements StateEvent {

    enum Type {
        SEND_MESSAGE, TIMEOUT_EXPIRES, RECEIVE_RIR;
    }

    AppEvent request;
    AppEvent answer;
    Type type;

    Event(Type type, AppEvent request, AppEvent answer) {
        this.type = type;
        this.answer = answer;
        this.request = request;
    }

    @SuppressWarnings("unchecked")
    public <E> E encodeType(Class<E> eClass) {
        return eClass == Type.class ? (E) type : null;
    }

    @SuppressWarnings("rawtypes")
    public Enum getType() {
        return type;
    }

    public AppEvent getRequest() {
        return request;
    }

    public AppEvent getAnswer() {
        return answer;
    }

    public int compareTo(Object o) {
        return 0;
    }

    public Object getData() {
        return request != null ? request : answer;
    }

    public void setData(Object data) {
        try {
            if (((AppEvent) data).getMessage().isRequest()) {
                request = (AppEvent) data;
            } else {
                answer = (AppEvent) data;
            }
        } catch (InternalException e) {
            throw new IllegalArgumentException(e);
        }
    }
}