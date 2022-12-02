package com.example.example_mod;

import net.minecraft.util.Identifier;
import java.lang.System;
public class FlowerIDs {
	public static Identifier[] IDs = new Identifier[]{
			new Identifier("minecraft","dandelion"),
			new Identifier("minecraft","poppy"),
			new Identifier("minecraft","blue_orchid"),
			new Identifier("minecraft","allium"),
			new Identifier("minecraft","azure_bluet"),
			new Identifier("minecraft","red_tulip"),
			new Identifier("minecraft","orange_tulip"),
			new Identifier("minecraft","white_tulip"),
			new Identifier("minecraft","pink_tulip"),
			new Identifier("minecraft","oxeye_daisy"),
			new Identifier("minecraft","cornflower"),
			new Identifier("minecraft","lily_of_the_valley"),
			new Identifier("minecraft","wither_rose")
	};
	static {sort(IDs);}
	public static void merge(Identifier[] FlowerIdentifiers, int left, int middle, int right){
		int size1 = middle-left+1;
		int size2 = right-middle;

		Identifier[] Left = new Identifier[size1];
		Identifier[] Right = new Identifier[size2];

		System.arraycopy(FlowerIdentifiers, left,Left,0,size1);
		System.arraycopy(FlowerIdentifiers, middle,Right,0,size2);
		/*for (int i = 0; i < size1; i++){
			Left[i] = FlowerIdentifiers[left+i];
		}
		for (int j = 0; j < size2; j++){
			Right[j] = FlowerIdentifiers[middle+1+j];
		}*/

		int i = 0, j = 0;

		int k = left;

		while(i<size1&&j<size2){
			if (Left[i].compareTo(Right[j])<=0){
				FlowerIdentifiers[k] = Left[i];
				i++;
			} else {
				FlowerIdentifiers[k] = Right[j];
				j++;
			}
			k++;
		}
		while (i<size1) {
			FlowerIdentifiers[k] = Left[i];
			i++;
			k++;
		}
		while (j<size2) {
			FlowerIdentifiers[k] = Right[j];
			j++;
			k++;
		}
	}
	public static void sort(Identifier[] FlowerIdentifiers, int left, int right){
		if (left<right) {
			int middle = left + (right - left) / 2;
			sort(FlowerIdentifiers, left, middle);
			sort(FlowerIdentifiers, middle + 1, right);

			merge(FlowerIdentifiers, left, middle, right);
		}
	}
	public static void sort(Identifier[] FlowerIdentifiers){
		sort(FlowerIdentifiers, 0,FlowerIdentifiers.length-1);
	}


	public static boolean binarySearch(Identifier[] FlowerIdentifiers, Identifier Target, int left, int right){
		if (right>=left) {
			int middle = left + (right - left) / 2;
			int result = FlowerIdentifiers[middle].compareTo(Target);
			if (result == 0) {
				return true;
			}
			if (result < 0) {
				return binarySearch(FlowerIdentifiers, Target, middle+1, right);
			}
			return binarySearch(FlowerIdentifiers, Target, left, middle-1);
		}
		return false;
	}
	public static boolean binarySearch(Identifier Target){
		return binarySearch(IDs, Target,0,IDs.length-1);
	}
}
