#include "MakeFileParser.h"
#include "MakeException.h"
#include "StringUtil.h"

#include <ctype.h>
#include <assert.h>
#include <sstream>
#include <memory>
#include <fstream>
using namespace std;


MakeFileParser::MakeFileParser() {
	return;
}


MakeFileParser::~MakeFileParser() {
	return;
}


DependencyGraph * MakeFileParser::Parse(istream & makeFile) {

	auto_ptr<DependencyGraph> dependencyGraph(new DependencyGraph());

	MacroProcessor macroProcessor;
	int lineNumber = 0;
	bool parsingTargetCommands = false;
	Target * currentTarget = 0;
	bool alreadyHasCommands = false;
	string line;

	while (ReadLogicalLine(makeFile, line, lineNumber)) {
		Target * target = 0;
		Command newCommand;
		
		macroProcessor.ExpandMacros(line);

		if (ParseEmptyLine(line)) {
			// skip empty lines
		}
		else if (ParseCommandLine(line, newCommand)) {
			if (parsingTargetCommands) {			
				if (alreadyHasCommands) {
					throw MakeException(MultipleCommandListsErrorMessage(currentTarget->GetName()));
				}
				currentTarget->GetCommands()->push_back(newCommand);
			}
			else {
				throw MakeException(SyntaxErrorMessage(lineNumber));
			}
		}
		else if (ParseMacroLine(line, macroProcessor)) {
			if (parsingTargetCommands) {
				parsingTargetCommands = false;
				currentTarget = 0;
				alreadyHasCommands = false;
			}
		}
		else if (ParseDependencyLine(line, dependencyGraph.get(), target)) {
			if (parsingTargetCommands) {
				parsingTargetCommands = false;
				currentTarget = 0;
				alreadyHasCommands = false;
			}
			
			parsingTargetCommands = true;
			currentTarget = target;
			alreadyHasCommands = !target->GetCommands()->empty();
		}
		else {
			throw MakeException(SyntaxErrorMessage(lineNumber));
		}
	}
	
	return dependencyGraph.release();
}


string MakeFileParser::SyntaxErrorMessage(int lineNumber) {
	ostringstream message;
	message << "syntax error on line: " << lineNumber;
	return message.str();
}


string MakeFileParser::MultipleCommandListsErrorMessage(const string & targetName) {
	return "multiple command lists specified for target: " + targetName;
}


bool MakeFileParser::ReadLogicalLine(istream & is, string & line, int & lineNumber) {

	line = "";

	bool gotAnyLines = false;

	while (true) {
		string physicalLine;
		if (ReadPhysicalLine(is, physicalLine)) {				
			gotAnyLines = true;
			++lineNumber;
			RemoveComments(physicalLine);
			
			if (LineIsContinued(physicalLine)) {
				RemoveLineContinuation(physicalLine);
				line += physicalLine;
			}
			else {
				line += physicalLine;
				break;
			}
		}
		else {
			break;
		}
	}

	return gotAnyLines;
}


bool MakeFileParser::ParseMacroLine(const string & line, MacroProcessor & macroProcessor) {
	string::size_type pos = 0;
	
	ParseWhitespace(line, pos);
	
	string macroName;
	if (ParseMacroName(line, pos, macroName)) {
		
		ParseWhitespace(line, pos);
			
		if (ParseExpected(line, pos, '=')) {
			string macroValue = line.substr(pos);
			StringUtil::Trim(macroValue);
			macroProcessor.DefineMacro(macroName, macroValue);
			return true;
		}
	}
	
	return false;
}


bool MakeFileParser::ParseDependencyLine(const string & line, DependencyGraph * dependencyGraph,
											Target * & target) {
	string::size_type pos = 0;
	
	ParseWhitespace(line, pos);
	
	string targetName;
	if (ParseTargetName(line, pos, targetName)) {
		ParseWhitespace(line, pos);
		
		if (ParseExpected(line, pos, ':')) {
			vector<string> dependencyList;
			
			if (ParseDependencyList(line, pos, dependencyList)) {
				target = AddDependenciesToTarget(dependencyGraph, targetName, dependencyList);
				return true;
			}
		}
	}
	
	return false;
}


Target * MakeFileParser::AddDependenciesToTarget(DependencyGraph * dependencyGraph, 
													const string & targetName, 
													vector<string> & dependencyList) {
														
	Target * target = dependencyGraph->GetTarget(targetName);
	
	vector<string>::iterator it;
	for (it = dependencyList.begin(); it != dependencyList.end(); ++it) {
		Target * dependencyTarget = dependencyGraph->GetTarget(*it);
		target->GetDependencies()->push_back(dependencyTarget);
	}
	
	return target;
}


bool MakeFileParser::ParseEmptyLine(const string & line) {
	for (string::size_type pos = 0; pos < line.length(); ++pos) {
		if (!isspace(line[pos])) {
			return false;
		}
	}
	return true;
}


bool MakeFileParser::ParseCommandLine(const string & line, Command & newCommand) {

	// command lines must begin with a tab
	if (line.length() == 0 || line[0] != '\t') {
		return false;
	}
	
	string commandText = line;
	StringUtil::Trim(commandText);
	
	bool display = true;
	bool haltOnError = true;
	while (commandText.length() > 0 && (commandText[0] == '-' || commandText[0] == '@')) {
		switch (commandText[0]) {
			case '-': haltOnError = false; break;
			case '@': display = false; break;
		}
		
		commandText.erase(0, 1);	// remove modifier character
		StringUtil::Trim(commandText);
	}
	
	if (commandText.length() > 0) {
		newCommand.SetText(commandText);
		newCommand.SetDisplay(display);
		newCommand.SetHaltOnError(haltOnError);
		return true;
	}
	else {
		return false;
	}
}


void MakeFileParser::ParseWhitespace(const string & line, string::size_type & pos) {
	while (pos < line.length() && isspace(line[pos])) {
		++pos;
	}
}


bool MakeFileParser::ParseMacroName(const string & line, string::size_type & pos, 
										string & macroName) {
	macroName = "";
	
	while (pos < line.length() && (isalnum(line[pos]) || line[pos] == '_')) {
		macroName += line[pos];
		++pos;
	}
	
	return (macroName.length() > 0);
}
	
	
bool MakeFileParser::ParseTargetName(const string & line, string::size_type & pos, 
										string & targetName) {
	targetName = "";
	
	while (pos < line.length() && !isspace(line[pos]) && line[pos] != ':') {
		targetName += line[pos];
		++pos;
	}
	
	return (targetName.length() > 0);
}


bool MakeFileParser::ParseExpected(const string & line, string::size_type & pos, char expected) {
	if (pos < line.length() && line[pos] == expected) {
		++pos;
		return true;
	}
	else {
		return false;
	}
}


bool MakeFileParser::ParseDependencyList(const string & line, string::size_type & pos,
											vector<string> & dependencyNames) {
												
	dependencyNames.clear();
	
	while (true) {
		ParseWhitespace(line, pos);
		
		string targetName;
		if (ParseTargetName(line, pos, targetName)) {
			dependencyNames.push_back(targetName);
		}
		else {
			if (pos == line.length()) {
				return true;
			}
			else {
				dependencyNames.clear();
				return false;
			}
		}
	}
}


void MakeFileParser::RemoveComments(string & line) {
	string::size_type sharpPos = line.find('#');
	if (sharpPos != string::npos) {
		line.erase(sharpPos);
	}
}


bool MakeFileParser::ReadPhysicalLine(istream & is, string & line) {

	line = "";

	int c = is.get();
	if (c < 0) {
		return false;
	}

	while (c >= 0 && c != '\n') {
		line += (char)c;
		c = is.get();
	}
	
	return true;
}


bool MakeFileParser::LineIsContinued(const string & line) {
	
	string::size_type slashPos = line.rfind('\\');
	if (slashPos == string::npos) {
		return false;
	}

	string::size_type pos = slashPos + 1;
	while (pos < line.length()) {
		if (!isspace(line[pos])) {
			return false;
		}
		else {
			++pos;
		}
	}

	return true;
}


void MakeFileParser::RemoveLineContinuation(string & line) {

	assert(LineIsContinued(line));
	line.erase(line.rfind('\\'));
}


bool MakeFileParser::Test(ostream & os) {

	ifstream makeFile("test/makefile.test");
	if (!makeFile) {
		return false;
	}
	
	MakeFileParser parser;
	DependencyGraph * graph = parser.Parse(makeFile);
//	graph->Print(os);
	delete graph;
	
	return true;
}








