#!/bin/bash

prefix="Number=#"
suffix="_Size="
for orig in `ls *.png`;
do
    	#echo "original is "$orig
	filename=`basename $orig .png`
	foo=${filename#"$prefix"}
	foo=${foo#"$suffix"}    
	mv $orig "$foo".png;
done

ls *.png
