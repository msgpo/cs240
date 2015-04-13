#ifndef MAKE_FILE_PARSER_H
#define MAKE_FILE_PARSER_H

#include "DependencyGraph.h"
#include "MacroProcessor.h"
#include "Command.h"

#include <string>
#include <iostream>
using namespace std;


class MakeFileParser {

public:
	static bool Test(ostream & os);

public:
	MakeFileParser();
	~MakeFileParser();

	DependencyGraph * Parse(istream & makeFile);

private:
	bool ReadLogicalLine(istream & is, string & line, int & lineNumber);
	bool ReadPhysicalLine(istream & is, string & line);
	void RemoveComments(string & line);
	bool LineIsContinued(const string & line);
	void RemoveLineContinuation(string & line);

	bool ParseMacroLine(const string & line, MacroProcessor & macroProcessor);
	bool ParseDependencyLine(const string & line, DependencyGraph * dependencyGraph, 
								Target * & newTarget);
	bool ParseEmptyLine(const string & line);
	bool ParseCommandLine(const string & line, Command & newCommand);

	Target * AddDependenciesToTarget(DependencyGraph * dependencyGraph, 
										const string & targetName, 
										vector<string> & dependencyList);

	void ParseWhitespace(const string & line, string::size_type & pos);
	bool ParseMacroName(const string & line, string::size_type & pos, 
							string & macroName);
	bool ParseTargetName(const string & line, string::size_type & pos, 
							string & targetName);
	bool ParseExpected(const string & line, string::size_type & pos, char expected);
	bool ParseDependencyList(const string & line, string::size_type & pos,
								vector<string> & dependencyNames);
	
	string SyntaxErrorMessage(int lineNumber);
	string MultipleCommandListsErrorMessage(const string & targetName);

};


#endif

