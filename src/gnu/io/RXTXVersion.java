/*-------------------------------------------------------------------------
|   RXTX License v 2.1 - LGPL v 2.1 + Linking Over Controlled Interface.
|   RXTX is a native interface to serial ports in java.
|   Copyright 1997-2009 by Trent Jarvi tjarvi@qbang.org and others who
|   actually wrote it.  See individual source files for more information.
|
|   A copy of the LGPL v 2.1 may be found at
|   http://www.gnu.org/licenses/lgpl.txt on March 4th 2007.  A copy is
|   here for your convenience.
|
|   This library is free software; you can redistribute it and/or
|   modify it under the terms of the GNU Lesser General Public
|   License as published by the Free Software Foundation; either
|   version 2.1 of the License, or (at your option) any later version.
|
|   This library is distributed in the hope that it will be useful,
|   but WITHOUT ANY WARRANTY; without even the implied warranty of
|   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
|   Lesser General Public License for more details.
|
|   An executable that contains no derivative of any portion of RXTX, but
|   is designed to work with RXTX by being dynamically linked with it,
|   is considered a "work that uses the Library" subject to the terms and
|   conditions of the GNU Lesser General Public License.
|
|   The following has been added to the RXTX License to remove
|   any confusion about linking to RXTX.   We want to allow in part what
|   section 5, paragraph 2 of the LGPL does not permit in the special
|   case of linking over a controlled interface.  The intent is to add a
|   Java Specification Request or standards body defined interface in the 
|   future as another exception but one is not currently available.
|
|   http://www.fsf.org/licenses/gpl-faq.html#LinkingOverControlledInterface
|
|   As a special exception, the copyright holders of RXTX give you
|   permission to link RXTX with independent modules that communicate with
|   RXTX solely through the Sun Microsytems CommAPI interface version 2,
|   regardless of the license terms of these independent modules, and to copy
|   and distribute the resulting combined work under terms of your choice,
|   provided that every copy of the combined work is accompanied by a complete
|   copy of the source code of RXTX (the version of RXTX used to produce the
|   combined work), being distributed under the terms of the GNU Lesser General
|   Public License plus this exception.  An independent module is a
|   module which is not derived from or based on RXTX.
|
|   Note that people who make modified versions of RXTX are not obligated
|   to grant this special exception for their modified versions; it is
|   their choice whether to do so.  The GNU Lesser General Public License
|   gives permission to release a modified version without this exception; this
|   exception also makes it possible to release a modified version which
|   carries forward this exception.
|
|   You should have received a copy of the GNU Lesser General Public
|   License along with this library; if not, write to the Free
|   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
|   All trademarks belong to their respective owners.
--------------------------------------------------------------------------*/
package gnu.io;

/**
A class to keep the current version in
*/

public class RXTXVersion
{
/*------------------------------------------------------------------------------
	RXTXVersion  
	accept:       -
	perform:      Set Version.
	return:       -
	exceptions:   Throwable
	comments:     
		      See INSTALL for details.
------------------------------------------------------------------------------*/
	private static String VersionMajor;
	private static String Version;
	private static String NativeVersion;

	static {
		RXTXVersion.loadLibrary( "rxtxSerial" );
		VersionMajor = "RXTX-2.2";
		Version = VersionMajor + " (CVS snapshot 2011.02.03, modified by CMU CREATE Lab, http://code.google.com/p/create-lab-commons/)";
		String s;
		try {
			s = RXTXVersion.nativeGetVersion();
		} catch ( Error UnsatisfiedLinkError )
		{
			// for rxtx prior to 2.1.7
			s = nativeGetVersion();
		}
		NativeVersion = s;
	}

	/**
	 * static method to check the version of the native library.
	 * @param LibVersion the native library version.
	 * @return true if major version matches, false otherwise.
	 * @see #getNativeVersion()
	 */
	public static boolean checkVersion(String nativeVersion) {
		return nativeVersion.startsWith(VersionMajor);
	}
	
	/**
	 * static method to return the native library version of RXTX.
	 * @return a string representing the version  "RXTX-1.4-9"
	 */
	public static String getNativeVersion() {
		return NativeVersion;
	}
	
	/**
	 * static method to return the revision text of RXTX.
	 * @return the revision text of RXTX.
	 */
	public static String getRevisionText() {
		String s = "RXTXCommDriver version " + Version;
		if (Version.equals(NativeVersion)) {
			return s;
		} else {
			return s + "\nnative library version " + NativeVersion;
		}
	}

	/**
	*  static method to return the current version of RXTX
	*  unique to RXTX.
	*  @return a string representing the version  "RXTX-1.4-9"
	*/
	public static String getVersion()
	{
		return(Version);
	}
	public static native String nativeGetVersion();

 	static void loadLibrary (String baseName) {
		if (System.getProperty("sun.arch.data.model", "").equals("64")) {
			try {
				System.loadLibrary(baseName + "64");
			} catch (UnsatisfiedLinkError ex64) {
				try {
					System.loadLibrary(baseName);
				} catch (UnsatisfiedLinkError ignored) {
					throw ex64;
				}
			}
		} else {
			try {
				System.loadLibrary(baseName);
			} catch (UnsatisfiedLinkError ex32) {
				try {
					System.loadLibrary(baseName + "64");
				} catch (UnsatisfiedLinkError ignored) {
					throw ex32;
				}
			}
		}
	}

}
