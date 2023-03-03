#include <iostream>

//when you code in C++ to code in Java - gamma
std::string toLowerCase(std::string &input){
    std::string out;
    for(char c : input){
        out += tolower(c);
    }
    return out;
}

int main() {


    while(true){
        std::cout << "Please enter a new 1.12 block code:" << std::endl;
        std::string input;
        std::getline(std::cin, input);

        if(input == "exit"){
            break;
        }

        else if(input == ""){
            continue;
        }

        std::string input3;
        std::cout << "please enter block properties" << std::endl;
        std::getline(std::cin, input3);

//        std::cout << input.find("final ") << std::endl;

        std::string input2 = input;

//        std::copy(input.begin(), input.end(), input2.begin());
        input2.replace(input2.find("final "), 6, "");


        std::string CLass = input2.substr(14,  input2.find(" ", 14)-14);
        input2.replace(14, input2.find(" ", 15)-14, "RegistryObject<" + CLass + ">");


        int start = input2.find(CLass) + CLass.length()+1;
        std::string thing = input2.substr(start+1, input2.find(" ", start+1)-(start+1));
        input2.insert(input2.find("= ")+2, "modBlocks.register(\"" + toLowerCase(thing) + "\", () -> " );

        if(input2.find("BasicBlock") != std::string::npos)
            input2.replace(input2.find("BasicBlock"), 5, "");
        if(input2.find("Material.ROCK") != std::string::npos)
            input2.replace(input2.find("Material.ROCK")+9, 4, "STONE");
        if(input2.find("setHardness") != std::string::npos)
            input2.replace(input2.find("setHardness"), 11, "setStrength");

        input2.insert(input2.find_last_of("()"), input3);

        input2.insert(input2.length()-2, ")");


        std::cout << input2 << std::endl;

        std::cin.clear();




    }
    return 0;
}