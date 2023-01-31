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
For merging and merge conflict resolutions it was done on one a case to case basis either individually or in a smaller group, sort of as a ad-hoc pair programming style.