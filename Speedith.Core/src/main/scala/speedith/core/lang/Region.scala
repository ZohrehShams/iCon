package speedith.core.lang

import java.lang.StringBuilder
import java.util.Collections

import propity.util.Sets

import scala.collection.JavaConversions.{asJavaCollection, asScalaSet, iterableAsScalaIterable, setAsJavaSet}

@SerialVersionUID(1438848965274344333L)
case class Region(zones: Set[Zone]) extends Comparable[Region] with Serializable{  
  
  val sortedZones: java.util.SortedSet[Zone] = {
    Collections.unmodifiableSortedSet(
      new java.util.TreeSet[Zone](setAsJavaSet(zones))
    )
  }

  def this(zones: java.util.Set[Zone]) = {
    this(if (zones == null) Set.empty[Zone] else asScalaSet(zones).toSet)
  }

  def this(zones: java.util.Collection[Zone]) = {
    this(if (zones == null) Set.empty[Zone] else iterableAsScalaIterable(zones).toSet)
  }

  def this() = {
    this(Set.empty)
  }

  def this(zone1: Zone) = {
    this(Set(zone1))
  }

  def this(zone1: Zone, zone2: Zone) = {
    this(Set(zone1, zone2))
  }

  def this(zone1: Zone, zone2: Zone, zone3: Zone) = {
    this(Set(zone1, zone2, zone3))
  }

  def this(zone1: Zone, zone2: Zone, zone3: Zone, zone4: Zone) = {
    this(Set(zone1, zone2, zone3, zone4))
  }

  def this(zone1: Zone, zone2: Zone, zone3: Zone, zone4: Zone, otherZones: Zone*) = {
    this(Set(zone1, zone2, zone3, zone4) ++ otherZones)
  }

  def isSubregionOf(otherRegion: Region): Boolean = {
    zones.subsetOf(otherRegion.zones)
  }

  def contains(zone: Zone): Boolean = {
    zones.contains(zone)
  }

  def getZonesCount: Int = {
    zones.size
  }

  def union(otherRegion: Region): Region = {
    Region(zones ++ otherRegion.zones)
  }

  def subtract(otherRegion: Region): Region = {
    Region(zones -- otherRegion.zones)
  }

  def compareTo(otherRegion: Region): Int = {
    Sets.compareNaturally(sortedZones, otherRegion.sortedZones)
  }

  def toString(stringBuilder: Appendable): Appendable = {
    SpiderDiagram.printZoneList(stringBuilder, sortedZones)
    stringBuilder
  }

  override def toString: String = {
    toString(new StringBuilder()).toString
  }
  
  
  //Zohreh 
  def intersect(otherRegion: Region): Boolean = {
   val intersection = zones.intersect(otherRegion.zones)
   if(intersection.isEmpty){
     return false
   }else 
     return true
  }
    
    
}
