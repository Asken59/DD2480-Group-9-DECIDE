# DECIDE
A hypothetical anti-ballistic missile system to determine if a launch is authorized or not. 
The main functionality is implemented in the Decide class (located in the main module) with the static DECIDE() method.
The parameters and inputs of the method is implemented through the public variables
_PARAMETERS, X, Y, NUMPOINTS, LCM, PUM, PUV, CMV, FUV, and LAUNCH_.
For the exact details of the various inputs see the 
[specifications document](https://inst-fs-dub-prod.inscloudgate.net/files/5dd85091-c1d1-4b98-843d-7ef9c4931a98/decide.pdf?download=1&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2NzQ2MjE0NDQsInVzZXJfaWQiOiI4Nzc5MDAwMDAwMDA5NTc3OSIsInJlc291cmNlIjoiL2ZpbGVzLzVkZDg1MDkxLWMxZDEtNGI5OC04NDNkLTdlZjljNDkzMWE5OC9kZWNpZGUucGRmIiwianRpIjoiYTM4ZDI3MmEtMDk4Ny00ZGEyLWE2OWYtNDRhMjZjZDhhMGQ1IiwiaG9zdCI6ImNhbnZhcy5rdGguc2UiLCJvcmlnaW5hbF91cmwiOiJodHRwczovL2E4Nzc5LTYxNTc1NTAuY2x1c3RlcjE1LmNhbnZhcy11c2VyLWNvbnRlbnQuY29tL2NvdXJzZXMvODc3OX4zNzkxOC9maWxlcy84Nzc5fjYxNTc1NTAvY291cnNlJTIwZmlsZXMvZGVjaWRlLnBkZj9kb3dubG9hZF9mcmQ9MVx1MDAyNm5vX2NhY2hlPXRydWVcdTAwMjZyZWRpcmVjdD10cnVlXHUwMDI2c2ZfdmVyaWZpZXI9ZXlKMGVYQWlPaUpLVjFRaUxDSmhiR2NpT2lKSVV6VXhNaUo5LmV5SjFjMlZ5WDJsa0lqb2lPRGMzT1RBd01EQXdNREF3T1RVM056a2lMQ0p5YjI5MFgyRmpZMjkxYm5SZmFXUWlPaUk0TnpjNU1EQXdNREF3TURBd01EQXdNU0lzSW05aGRYUm9YMmh2YzNRaU9pSmpZVzUyWVhNdWEzUm9Mbk5sSWl3aWNtVjBkWEp1WDNWeWJDSTZiblZzYkN3aVptRnNiR0poWTJ0ZmRYSnNJam9pYUhSMGNITTZMeTlqWVc1MllYTXVhM1JvTG5ObEwyTnZkWEp6WlhNdk16YzVNVGd2Wm1sc1pYTXZOakUxTnpVMU1DOWtiM2R1Ykc5aFpEOW1ZV3hzWW1GamExOTBjejB4TmpjME5qUTVOalF4SWl3aVpYaHdJam94TmpjME5qUTVPVFF4ZlEuVXdyTDg1alN0RW9vMHZPMENKTW8wc3FvdDhxSFlpcndJSE5JbEs0dGZtTE03NDVrMU5ueVQ2TUhfRmdKa1RfLXFGV21VYm50UHdZb284MzhxVDUxYXciLCJleHAiOjE2NzQ3MDc4NDR9.S6fJDK0eBNAeY5tNtodSoG_PCs2TgUbgTQbrAD-12504M8NruiPiAhb3vgOCDJlTTEF-_CtlNE_TNelNoPPS6g).
Note that the _PARAMETERS_ variable is a custom object containing various constants.

## Branch and commit naming convention
In this repository the naming of branches and commits follows the convention of _{feat | doc | bug | etc}/{issue number}_.
E.g a commit adding the feature of issue #24 would start its message with _feat/24_.


## Tests
The repo does not come with a main method to run the DECIDE() method in, however several tests were written for various functionalities, located in src/tests/.
These are easiest run through an IDE such as IntelliJ IDEA and requires the JUnit 5 library.
It is also possible to compile and run them normally through the JVM, just compile them together with the JUnit 5 jars.

## Contributions
Each person wrote three different LICs, including implementation and tests. 
Merging and merge conflict resolutions were done on one a case to case basis either individually or in a smaller group, sort of as a ad-hoc pair programming style.
The rest of the work, like writing this README, updating comments or adding missing functionality was also done ad-hoc individually by the commiter or in a group as a sort of over the shoulder work style.

All pull requests were reviewed by at least one other member in the group, often someone that happened to be available at the time.

## Essence, our way of working
We evaluated that according to the [essence checklist](https://www.omg.org/spec/Essence/1.2/PDF) (p. 60), we are working on the "In Place" level.
This is because we fulfill all the checklist items before "In Place" in addition to the ones directly linked to "In Place".
All team members have access to the practices and tools we set out to use, and all team members actually use those tools and practices. Through meetings and online communication we are also all involved in the inspection and adaption of the way of working.

The reason we are not working on the "Working well" level yet is that we do not fulfill some requirements such as naturally applying all our practices without thinking about them.
