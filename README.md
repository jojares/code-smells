# code-smells
Project to detect harmful code smells from Java source code

I created this project to experiment with JavaParser's AST navigation using Visitor Pattern. I'm creating a Pro Gradu about code smells so I decided that it would be interesting to program some simple code smell detection algorithms.

Currently project has only "Long Parameter List" smell implemented which is very straightforward to implement. Generally Bloater smells are easy to detect as the metric used in detection is LOC or similar.

For more information about code smells, here's a few good papers:
- M. Fowler, Refactoring: Improving the Design of Existing Programs. Addison-Wesley, 1999
- N. Moha, Y. Gueheneuc, L. Duchien and A. Le Meur, "DECOR: A Method for the Specification and Detection of Code and Design Smells," in IEEE Transactions on Software Engineering, vol. 36, no. 1, pp. 20-36, Jan.-Feb. 2010
- Girish Suryanarayana, Ganesh Samarthyam, and Tushar Sharma. 2014. Refactoring for Software Design Smells: Managing Technical Debt (1st ed.). Morgan Kaufmann Publishers Inc., San Francisco, CA, USA
