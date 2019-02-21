# iCon 2

--------------------------------------------------------------------------------

iCon is a diagrammatic theorem prover for [conceptDiagrams] that builds on Speedith (https://github.com/speedith/speedith).


--------------------------------------------------------------------------------

# Licence

iCon is licenced under the MIT licence. Please read [LICENCE.md](LICENCE.md) for more information.

--------------------------------------------------------------------------------

# Developer's Guide to _iCon_ #

--------------------------------------------------------------------------------

## General Requirements

These requirements have to be checked only once. After you make sure you have
these, you can build Speedith at any time.

*   Maven (see [installation instructions](https://maven.apache.org/))

*   Java 8

--------------------------------------------------------------------------------

## Building iCon

Use Maven to build Speedith (in the root directory of your cloned iCon repository):

    mvn install

--------------------------------------------------------------------------------

## Running iCon

The build will result in a distributable package:

    Speedith.Gui/target/speedith-gui-0.0.1-SNAPSHOT-bin.zip

Unpack the archive and navigate into the unpacked `speedith/bin` folder.

Once in `speedith/bin` folder, execute the script that best matches your platform.

For example, on Windows you might want to execute either

    speedith-win32.bat

or

    speedith-win64.bat

and on Mac you might want to execute 

	main-form-executable-wrapper




