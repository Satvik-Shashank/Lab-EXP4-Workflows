# How to submit these two labs on GitHub

1. Create a new **public** GitHub repository (unless your faculty specified private). Suggested name: `camunda8-practical-labs-1-2`.
2. Extract the ZIP file to a folder on your computer.
3. Open PowerShell in that extracted folder and run:

```powershell
git init
git add .
git commit -m "Implement Camunda 8 Labs 1 and 2"
git branch -M main
git remote add origin https://github.com/YOUR-USERNAME/camunda8-practical-labs-1-2.git
git push -u origin main
```

Replace `YOUR-USERNAME` with your GitHub username and use the repository URL GitHub shows you. If the repository already contains a README or other files, clone it first and copy these folders into that clone instead of creating a conflicting history.

4. Complete the remaining Lab 2 gateway branches described in `report/IMPLEMENTATION_REPORT.md` before calling the work fully complete.
5. Run the workers and deploy both BPMN/DMN pairs to your Camunda 8 runtime. Capture real Tasklist/Operate screenshots and place them in `report/screenshots/`.
6. Update the test matrix with actual outcomes and commit/push the evidence:

```powershell
git add .
git commit -m "Add Camunda execution evidence and test results"
git push
```

7. Submit the GitHub repository URL if the PDF/LMS asks for a repository link. If the LMS explicitly asks for a ZIP too, upload the same extracted project re-zipped after adding your screenshots and completed report.

Never commit `.env`, client secrets, passwords, real patient data, real payment details, or access tokens. The examples in this project use synthetic identifiers and mock tokens.
