//============================================================================
// Name        : Project_Claudio.cpp
// Author      : Christian Claudio
// Version     : 1.0
// Copyright   : Copyright � 2023 SNHU COCE
// Description : Lab 7-2 Project Two
//============================================================================

#include <iostream>
#include <fstream>
#include <vector>
#include <Windows.h>
#include <string>
#include <time.h>
#include <sstream>
#include <algorithm>
#include <unordered_map> // Adding unordered_map library

using namespace std;

// Delclare a global variable to use for Sleep()
const int SLEEP_TIME = 6000;

// Data Structure CourseInfo
struct CourseInfo {
    string courseID;
    string courseName;

    std::vector<std::string> preReqList;
};
// Data structure of tree node holding course data
struct Node {
    CourseInfo course;
    Node* right;
    Node* left;

    // Constructor
    Node() {
        left = nullptr;
        right = nullptr;
    }
    //Initialization
    Node(CourseInfo aCourse) {
        course = aCourse;

        left = nullptr;
        right = nullptr;
    }
};

// Forward declarations
void deleteNodeTree(Node* node);
void displayCourse(CourseInfo course);

class BinarySearchTree {
private:
    ~BinarySearchTree();
    Node* root;
    void inOrder(Node* node);
    void addNode(Node* node, CourseInfo course);
    int size = 0;

public:
    BinarySearchTree();
    void InOrder();
    void Insert(CourseInfo aCourse);
    CourseInfo  Search(std::string courseID);
};

// Constructor
BinarySearchTree::BinarySearchTree() {
    this->root = nullptr;
}
// Destructor
BinarySearchTree::~BinarySearchTree() {
    if (root != nullptr) {
        deleteNodeTree(root);
    }
}
// Delete all nodes
void deleteNodeTree(Node* node) {
    if (node == nullptr) {
        return;
    }

    deleteNodeTree(node->left);
    deleteNodeTree(node->right);
    delete node;
}
// In Order Traversal
void BinarySearchTree::InOrder() {
    inOrder(root);
}
void BinarySearchTree::Insert(CourseInfo course) {
    if (root == nullptr) {
        root = new Node(course);
        return;
    }
    addNode(root, course);
}
CourseInfo BinarySearchTree::Search(std::string courseID) {
    CourseInfo course;

    while (root != nullptr) {
        if (root->course.courseID == courseID) {
            return root->course;
        }
        else if (courseID < root->course.courseID) {
            root = root->left;
        }
        else {
            root = root->right;
        }
    }

    return course;
}
void BinarySearchTree::addNode(Node* node, CourseInfo course) {
    if (node->course.courseID > course.courseID) {
        if (node->left == nullptr) {
            node->left = new Node(course);
        }
        else {
            addNode(node->left, course);
        }
    }
    else {
        if (node->right == nullptr) {
            node->right = new Node(course);
        }
        else {
            addNode(node->right, course);
        }
    }
}
void BinarySearchTree::inOrder(Node* node) {
    if (node == nullptr) {
        return;
    }
    inOrder(node->left);
    displayCourse(node->course);
    inOrder(node->right);
}
// Display courses
void displayCourse(CourseInfo course) {
    if (course.courseID.empty() || course.courseName.empty()) {
        return;
    }

    std::cout << course.courseID << ", " << course.courseName << std::endl;
    std::cout << "Prerequisites: ";

    if (course.preReqList.empty()) {

        std::cout << "no prerequisites" << std::endl;
    }

    for (int i = 0; i < course.preReqList.size(); i++) {

        std::cout << course.preReqList.at(i);

        if (course.preReqList.size() > 1 && i < course.preReqList.size() - 1) {

            std::cout << ", ";
        }
    }

    std::cout << std::endl;
}
// Takes in a string and uses the delimeter to spit the elements of the string into a vector which will be returned to the caller.
void split_str(std::string const& str, const char delim, std::vector <std::string>& out) {
    // Create a stream from the string
    std::stringstream s(str);

    std::string s2;
    while (std::getline(s, s2, delim))
    {
        out.push_back(s2); // store the string in s2  
    }
}
// Load courses
void loadCourseFile(std::string csvPath, BinarySearchTree* list, std::unordered_map<std::string, CourseInfo>& courseMap) {
    std::ifstream myfile;
    myfile.open(csvPath);


    if (myfile.is_open()) {
        while (myfile) { // equivalent to myfile.good()
            std::vector <std::string> items;
            std::string item;
            struct CourseInfo course;
            std::string myline;

            std::getline(myfile, myline);
            split_str(myline, ',', items);

            int size = items.size();

            if (size != 0) {
                course.courseID = items.at(0);
                course.courseName = items.at(1);

                std::string temp = items.at(2);
                course.preReqList.push_back(temp);
                if (size == 4) {
                    temp = items.at(3);
                    course.preReqList.push_back(temp);
                }
            }
            list->Insert(course); // Sorted Order (BST)
            courseMap[course.courseID] = course; // Instant Search (Hash Map)
        }
    }
    else {
        std::cout << "Couldn't open file\n";
    }

    std::cout << "Successfully Loaded Courses." << std::endl;
}
//Convert case of user input
std::string convertToUpper(std::string& upper) {

    for (int i = 0; i < upper.length(); i++) {
        if (isalpha(upper[i])) {
            upper[i] = std::toupper(upper[i]);
        }
    }
    return upper;
}
int main(int argc, char* argv[]) {
    // process command line arguments
    string csvPath, courseKey;
    switch (argc) {
    case 2:
        csvPath = argv[1];
        break;
    case 3:
        csvPath = argv[1];
        courseKey = argv[2];
        break;
    default:
        csvPath = "CS 300 ABCU_Advising_Program_Input.csv";
    }

    // Define the BST and Hash Map
    BinarySearchTree* list = new BinarySearchTree();
    std::unordered_map<std::string, CourseInfo> courseMap;

    CourseInfo course;
    bool validInput = false;
    int choice = 0;

    while (choice != 9) {
        std::cout << "Menu:" << std::endl;
        std::cout << "  1. Load Courses" << std::endl;
        std::cout << "  2. Display All Courses" << std::endl;
        std::cout << "  3. Find Course" << std::endl;
        std::cout << "  9. Exit" << std::endl;
        std::cout << "Enter Choice: ";

        try {
            std::cin >> choice;

            if ((choice > 0 && choice < 5) || (choice == 9)) {
                validInput = true;
            }
            else {
                validInput = false;
                throw 1;
            }

            switch (choice) {
            case 1:
                loadCourseFile(csvPath, list, courseMap); // Invoke parsing function

                Sleep(SLEEP_TIME);
                break;
            case 2:
                list->InOrder();

                Sleep(SLEEP_TIME);
                break;
            case 3: {
                std::cout << "What course do you want to know about? " << std::endl;
                std::cin >> courseKey;

                convertToUpper(courseKey); // Convert case for case insensitive input

                // Using the find() method of the Hash Map
                auto it = courseMap.find(courseKey);

                if (it != courseMap.end()) {
                    // The CourseInfo object is 'it->second'
                    displayCourse(it->second);
                }
                else {
                    std::cout << "Course ID " << courseKey << " was not found." << std::endl;
                }

                Sleep(SLEEP_TIME);
                break;
            }
            case 9:
                break;
            default:
                throw 2;
            }
        }
        catch (int error) {
            std::cout << "Invalid Input!" << std::endl;
            Sleep(SLEEP_TIME);
        }

        // Clear cin operator
        std::cin.clear();
        std::cin.ignore();

        // Clear console
        system("cls");
    }

    std::cout << "Good Bye." << std::endl;

    Sleep(SLEEP_TIME);
    return 0;
}