/*
  Function to render the footer content into the page
      Select the footer element from the DOM
      Set the inner HTML of the footer element to include the footer content
  This section dynamically generates the footer content for the web page, including the hospital's logo, copyright information, and various helpful links.

  1. Insert Footer HTML Content

     * The content is inserted into the `footer` element with the ID "footer" using `footer.innerHTML`.
     * This is done dynamically via JavaScript to ensure that the footer is properly rendered across different pages.

  2. Create the Footer Wrapper

     * The `<footer>` tag with class `footer` wraps the entire footer content, ensuring that it is styled appropriately.
       ```html
       <footer class="footer">
       ```

  3. Create the Footer Container

     * Inside the footer, a container div with the class `footer-container` holds the content to maintain proper alignment and spacing.
       ```html
       <div class="footer-container">
       ```

  4. Add the Hospital Logo and Copyright Info

     * A `footer-logo` div contains the hospital's logo (an image element) and the copyright information.
       - The `<img>` tag displays the logo, with an `alt` attribute for accessibility.
       - The copyright text is displayed in a paragraph element.
       ```html
       <div class="footer-logo">
         <img src="../assets/images/logo/logo.png" alt="Hospital CMS Logo">
         <p>© Copyright 2025. All Rights Reserved by Hospital CMS.</p>
       </div>
       ```

  5. Create the Links Section

     * A `footer-links` div contains all the links grouped into three sections: Company, Support, and Legals.
     * This structure helps to organize the footer content and makes it easier for users to find related links.

  6. Add the 'Company' Links Column

     * Inside the `footer-links` div, the first column represents company-related links.
       - The section includes a header (`<h4>Company</h4>`) followed by links for "About", "Careers", and "Press".
       ```html
       <div class="footer-column">
         <h4>Company</h4>
         <a href="#">About</a>
         <a href="#">Careers</a>
         <a href="#">Press</a>
       </div>
       ```

  7. Add the 'Support' Links Column

     * The second column is dedicated to support-related links.
       - It includes a header (`<h4>Support</h4>`) followed by links for "Account", "Help Center", and "Contact Us".
       ```html
       <div class="footer-column">
         <h4>Support</h4>
         <a href="#">Account</a>
         <a href="#">Help Center</a>
         <a href="#">Contact Us</a>
       </div>
       ```

  8. Add the 'Legals' Links Column

     * The third column contains legal-related links, such as "Terms & Conditions", "Privacy Policy", and "Licensing".
       - The header (`<h4>Legals</h4>`) is followed by these links.
       ```html
       <div class="footer-column">
         <h4>Legals</h4>
         <a href="#">Terms & Conditions</a>
         <a href="#">Privacy Policy</a>
         <a href="#">Licensing</a>
       </div>
       ```

  9. Close the Footer Container

     * Close the `footer-container` div to ensure proper structure.
       ```html
       </div> <!-- End of footer-container -->
       ```

  10. Close the Footer Element

     * Finally, close the `<footer>` tag to complete the footer section.
       ```html
       </footer>
       ```

  11. Footer Rendering Complete

     * The `footer.innerHTML` code completes the dynamic rendering of the footer by injecting the structured HTML content into the `footer` element on the page.



  Call the renderFooter function to populate the footer in the page

*/
