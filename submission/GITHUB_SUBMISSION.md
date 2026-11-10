# Submit to GitHub

1. Extract the ZIP.
2. Create a **Public** or faculty-approved **Private** GitHub repository named `camunda8-practical-labs-1-2`.
3. Open PowerShell in the extracted project root (the folder containing this README).
4. Run:

```powershell
git init
git add .
git commit -m "Complete Camunda 8 practical Labs 1 and 2"
git branch -M main
git remote add origin https://github.com/YOUR-USERNAME/camunda8-practical-labs-1-2.git
git push -u origin main
```

5. Replace `YOUR-USERNAME` with your GitHub username.
6. Paste the repository URL into the LMS/assignment submission field. If the faculty requests a ZIP instead, upload this ZIP plus the report and screenshots folder.
7. Before final submission, add your genuine screenshots under `evidence/` and complete `report/TEST_RESULTS.md`.

Never commit `.env`, client secrets, passwords, or real applicant/payment data.
