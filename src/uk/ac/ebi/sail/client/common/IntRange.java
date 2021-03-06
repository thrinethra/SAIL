/**
 * SAIL - biological samples availability index
 * 
 * Copyright (C) 2008,2009 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 *   This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *
 *  @author Mikhail Gostev <gostev@ebi.ac.uk>
 *
 */

package uk.ac.ebi.sail.client.common;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class IntRange implements Serializable, IsSerializable
{
 private int   partID;
 private int limitLow;
 private int limitHigh;

 public IntRange()
 {}
 
 public IntRange(int partID, int limitLow, int limitHigh)
 {
  super();
  this.partID = partID;
  this.limitLow = limitLow;
  this.limitHigh = limitHigh;
 }

 public IntRange(IntRange r)
 {
  partID = r.partID;
  limitHigh = r.limitHigh;
  limitLow = r.getLimitLow();
 }

 public int getPartID()
 {
  return partID;
 }

 public void setPartID( int ptid )
 {
  partID = ptid;
 }

 public int getLimitLow()
 {
  return limitLow;
 }

 public int getLimitHigh()
 {
  return limitHigh;
 }

 public void setLimitLow(int ll)
 {
  limitLow = ll;
 }

 public void setLimitHigh(int ul)
 {
  limitHigh = ul;
 }

 public String toString()
 {
  String txt;

  if(getLimitLow() != Integer.MIN_VALUE)
   txt = "[ " + getLimitLow();
  else
   txt = "( -\u221e";

  txt += " , ";

  if(getLimitHigh() != Integer.MAX_VALUE )
   txt += getLimitHigh() + " ]";
  else
   txt += "+\u221e )";

  return txt;
 }
}
