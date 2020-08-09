# LF-GDPR
This provides the implementation of our paper "LF-GDPR: A Framework for Estimating Graph Metrics with Local Differential Privacy", together with six public datasets for testing.

# Background
Local differential privacy (LDP) is an emerging technique for privacy-preserving data collection without a trusted collector. Despite its strong privacy guarantee, LDP cannot be easily applied to real-world graph analysis tasks such as community detection and centrality analysis due to its high implementation complexity and low data utility. In this paper, we address these two issues by presenting LF-GDPR, the first LDP-enabled graph metric estimation framework for graph analysis. It collects two atomic graph metrics, i.e., the adjacency bit vector and node degree, from each node locally. LF-GDPR simplifies the job of implementing LDP-related steps (e.g., local perturbation, aggregation and calibration) for a graph metric estimation task by providing either a complete or a parameterized algorithm for each step. To address low data utility of LDP, it optimally allocates privacy budget between the two atomic metrics during data collection. To demonstrate the usage of LF-GDPR, we show use cases on two common graph analysis tasks, namely, clustering coefficient estimation and community detection. The privacy and utility achieved by LF-GDPR are verified through theoretical analysis and extensive experimental results.

# Description (How to run)
Two functions to run:
1. src/com/graph/Main/TestMetric.java: calculate the real/estimated clustering coefficient of each node in the graph
2. src/com/graph/Main/DedicatedSolution.java: calculate estimated clustering coefficient/modularity via dedicated LDP solutions
  
Both of the two functions TestMetric and DedicatedSolution take as input an integer parameter (1-6) to specify which dataset will be used:
1. facebook_combined   
2. Email-Enron
3. CA-AstroPh-transform
4. Brightkite_edges
5. twitter_combined_transform
6. gplus_combined_transform

# Contact information
Please feel free to send email to qingqiingcs.ye@gmail.com, if you have any problem.
