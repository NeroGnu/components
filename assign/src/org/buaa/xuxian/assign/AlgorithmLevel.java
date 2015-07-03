package org.buaa.xuxian.assign;

/**
 * This enum is used to control assign algorithm level of update() method in class Assign.
 * ALGOR_LEVEL_1 means the normal maximum matching in bipartite graphs.
 * ALGOR_LEVEL_2 add the dynamic time threshold.
 * ALGOR_LEVEL_3 add the dynamic distance threshold, which is not realized.
 * @see Class Assign
 * @author Nero
 * @version v1.0
 *
 */
public enum AlgorithmLevel {
	/**ALGOR_LEVEL_1 means the normal maximum matching in bipartite graphs.*/
	ALGOR_LEVEL_1, 
	/**ALGOR_LEVEL_2 add the dynamic time threshold.*/ 
	ALGOR_LEVEL_2, 
	/**ALGOR_LEVEL_3 add the dynamic distance threshold, which is not realized.*/
	ALGOR_LEVEL_3
}
