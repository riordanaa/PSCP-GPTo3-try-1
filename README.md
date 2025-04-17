# [PSSC](https://doi.org/XXXXX)

Original work: https://link.springer.com/article/10.1007/s10732-025-09552-7
Original repo: https://github.com/LmR308/PSSC

## Abstract
The partial set covering problem (PSCP) is a significant combinatorial optimization problem that finds applications in numerous real-world scenarios. The objective of PSCP is to encompass a minimum number of subsets while ensuring the coverage of at least n elements. Due to its NP-hard nature, solving large-scale PSCP efficiently remains a critical issue in computational intelligence. 

## Authors
Authors involved in this work and their respective contributions:
- Person1.
- Person2.
## Datasets

Instances are categorized in different datasets inside the 'instances' folder. All instances are from the [LmR308/PSSC]([https://archive.ics.uci.edu/ml/index.php](https://github.com/LmR308/PSSC)).

### Instance format

(Explain instance format so other users may easily use them even if not using your code.)

### Solution format
First line contains the number of sets (n), which is the objective function that we have to minimize.
Second line contains n integers, each one representing the chosen set whose elements are covered.


## Compiling
An executable version of the project is available at the root folder, called `PSSC.jar`. You may recompile the project using using Maven and a recent version of Java (17+):

```shell
mvn clean package
```

Executable artifacts are generated inside the `target` by default.


## Executing
### Validating solutions

You can validate the solution using the `validate` command. This command will check if the solution is valid and if it covers all the elements.

Example of an unfeasible solution:

```shell
java -jar PSSC.jar validate instances/scp41.txt solutions/testSolution1.txt
```

Example of a feasible solution:

```shell
java -jar PSSC.jar validate instances/scp41.txt solutions/testSolution2.txt
``` 

### Executing solver
WARNING: Not implemented yet.

For ease of use, there is an already executable JAR in the root of the project.
To review a full list of configurable parameters, see the `application.yml`, or review the [configuration section of the Mork documentation](https://docs.mork-optimization.com/en/latest/features/config/).

Example 1: execute default experiment with the default set of instances
```text
java -jar target/PSSC.jar 
```

Example: execute the IteratedGreedyExperiment using a different set of instances, located inside the `newinstances` folder.
```
java -jar target/PSSC.jar --instances.path.default=newinstances --solver.experiment=IteratedGreedyExperiment
```

## Cite

Consider citing our paper if used in your own work:
(Fill with the references to your own published work)

### DOI
https://doi.org/XXXXXXX

### Bibtex
```bibtex
@article{
...
}
```

## Powered by MORK (Metaheuristic Optimization framewoRK)
| ![Mork logo](https://user-images.githubusercontent.com/55482385/233611563-4f5c91f2-af36-4437-a4b5-572b6655487a.svg) | Mork is a Java framework for easily solving hard optimization problems. You can [create a project](https://generator.mork-optimization.com/) and try the framework in under one minute. See the [documentation](https://docs.mork-optimization.com/en/latest/) or the [source code](https://github.com/mork-optimization/mork). |
|--|--|
