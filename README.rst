============================
Search Quality Automated
============================

-----------
Description
-----------
No matter how cool your search solution is, without a reliable way of testing it you will not be able to do any improvement and do not break something valuable. 

Even a tiny increase in search result quality might have a noticeable effect on conversion rate.
Naturally, you can use exit-rate to judge if your search needs improvements and do required adjustments, but will you be able to make sure those changes are safe? What if improved relevancy for a specific category breaks search for several other categories?

Moreover, high exit-rate means no matter what you are going to do, the problem has taken place, so dozens of customers already didn’t manage to find what they were up to. Basically, the only way to proceed is to have a reliable search quality validation approach.

--------------------------------
Intent-based validation
--------------------------------
Clear intent makes search result validation way more simpler and straightforward. There are several validation strategies possible. The actual strategy implementation is out of scope of this project. 

Search Quality API defines the following set of "verdicts" for a product being returned: relevant, near, misplaced, irrelevant, undefined:

Relevant 
  The product ranking is completely relevant.

Near 
  The product ranking is not a perfect match, but it is clearly reasonable 
  for it to be ranked highly.

Misplaced 
  It is obvious why the search engine returned the result, but it clearly shouldn't be ranked highly.

Irrelevant
  The result has no apparent relationship to the customer’s search.

Undefined 
  For some reason the result is undefined and can not be validated.

Intent-based approach is pretty intuitive and straightforward. So, if you are searching for "fiskars splitting axe" you do not expect to see any other brands, any other tools etc. Obviously, chopping axe is definitely not an option, as well as any other type on an axe.

.. image:: doc/images/amazon-example-01.png?raw=true
   :align: center

Searching visitors usually have more specific shopping intents, comparing to visitors who are simply browsing, so it is crucial to make sure search always works as expected. 

--------
Features
--------
- Extendable test set
- Automated and reliable search quality verification
- Various validation/verification strategies
- Detailed reporting with root cause analysis
- Historical trends

Trends
-------------
High-level statistics is available in a way it can help to quickly understand the actual situation and overall trend. 

.. image:: doc/images/a06.png?raw=true
   :align: center

Passing tests
--------------
Passing test still provides all the details regarding the actual result set and the products. So, if any deep inspection needed it can be done.

.. image:: doc/images/a03.png?raw=true
   :align: center

Failing test
--------------
It case test fails deep investigation is a must, so the solution provides all the required details, including the list of the product returned for the query. Useful marks highlights non relevant products, simplifying analysis.   

.. image:: doc/images/a02.png?raw=true
   :align: center

How to run
--------------
The package contains two scripts:

report.sh
    Executes the test set and updates the history.
    
web.sh
    Starts python SimpleHTTPServer on 9040

---------------------------
How to modify the test set
---------------------------
The test set itself is just an ordinary CSV document (located at *src/test/resources/search-queries.csv*)

::

    Critical,fiskars axe
    Critical,fiskars splitting axe
    Critical,fiskars super splitting axe
    Critical,fiskars chopping axe
    Major   ,fiskars axe XXS
    Major   ,fiskars axe XS
    Major   ,fiskars axe S

-------
License
-------
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.