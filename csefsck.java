/**
 * 
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class csefsck 
{
	public static void main(String[] args) throws IOException 
	{		
		FileSystemChecker checker=new FileSystemChecker();
		checker.fileCheckerFunc();
	}
}

class SuperBlock
{
	private long creationTime;
	private int mounted;
	private int devId;
	private int freeStart;
	private int freeEnd;
	private int root;
	private int maxBlock;
	
	@Override
	public String toString() {
		return "creationTime="+getCreationTime()+" mounted="+getMounted()+" devId="+getDevId()+" freeStart="+getFreeStart()
				+" freeEnd="+getFreeEnd()+" root="+getRoot()+" maxBlock="+getMaxBlock();
	}
	
	public long getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}
	public int getMounted() {
		return mounted;
	}
	public void setMounted(int mounted) {
		this.mounted = mounted;
	}
	public int getDevId() {
		return devId;
	}
	public void setDevId(int devId) {
		this.devId = devId;
	}
	public int getFreeStart() {
		return freeStart;
	}
	public void setFreeStart(int freeStart) {
		this.freeStart = freeStart;
	}
	public int getFreeEnd() {
		return freeEnd;
	}
	public void setFreeEnd(int freeEnd) {
		this.freeEnd = freeEnd;
	}
	public int getRoot() {
		return root;
	}
	public void setRoot(int root) {
		this.root = root;
	}
	public int getMaxBlock() {
		return maxBlock;
	}
	public void setMaxBlock(int maxBlock) {
		this.maxBlock = maxBlock;
	}
}

class FileNameInodeDict
{
	private String fileOrDir;
	private String fileDirName;
	private int blockNo;
	
	@Override
	public String toString() {
		return "fileOrDir="+getFileOrDir()+" fieDirName"+getFileDirName()+" blockNo="+getBlockNo();
	}
	
	public String getFileOrDir() {
		return fileOrDir;
	}
	public void setFileOrDir(String fileOrDir) {
		this.fileOrDir = fileOrDir;
	}
	public String getFileDirName() {
		return fileDirName;
	}
	public void setFileDirName(String fieDirName) {
		this.fileDirName = fieDirName;
	}
	public int getBlockNo() {
		return blockNo;
	}
	public void setBlockNo(int blockNo) {
		this.blockNo = blockNo;
	}
}

class DirBlock
{
	private int size;
	private int uid;
	private int gid;
	private int mode;
	private long atime;
	private long ctime;
	private long mtime;
	private int linkCount;
	private int fusedataNo;
	private List<FileNameInodeDict> inodeDictList;
	
	@Override
	public String toString() {
		return "size="+getSize()+" uid="+getUid()+" gid="+getGid()+" mode="+getMode()+" atime="+getAtime()
				+" ctime="+getCtime()+" mtime="+getMtime()+" linkcount="+getLinkCount()+
				" list_len="+(getInodeDictList()!=null?getInodeDictList().size():0)+" fusedata.no="+getFuseDataNo();
	}
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public long getAtime() {
		return atime;
	}
	public void setAtime(long atime) {
		this.atime = atime;
	}
	public long getCtime() {
		return ctime;
	}
	public void setCtime(long ctime) {
		this.ctime = ctime;
	}
	public long getMtime() {
		return mtime;
	}
	public void setMtime(long mtime) {
		this.mtime = mtime;
	}
	public int getLinkCount() {
		return linkCount;
	}
	public void setLinkCount(int linkCount) {
		this.linkCount = linkCount;
	}
	public int getFuseDataNo() {
		return fusedataNo;
	}
	public void setFuseDataNo(int fusedataNo) {
		this.fusedataNo = fusedataNo;
	}
	public List<FileNameInodeDict> getInodeDictList() {
		return inodeDictList;
	}
	public void setInodeDictList(List<FileNameInodeDict> inodeDictList) {
		this.inodeDictList = inodeDictList;
	}
}

class FileInode
{
	private int size;
	private int uid;
	private int gid;
	private int mode;
	private int linkCount;
	private long atime;
	private long ctime;
	private long mtime;
	private int indirect;
	private int location;
	private int fusedataNo;
	
	public FileInode() {

	}
	@Override
	public String toString() {
		return "size="+getSize()+" uid="+getUid()+" gid="+getGid()+" mode="+getMode()+" atime="+getAtime()
				+" ctime="+getCtime()+" mtime="+getMtime()+" linkcount="+getLinkCount()+" indirect="+getIndirect()
				+" location="+getLocation()+" fusedata.no="+getFusedataNo();
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public int getLinkCount() {
		return linkCount;
	}
	public void setLinkCount(int linkCount) {
		this.linkCount = linkCount;
	}
	public long getAtime() {
		return atime;
	}
	public void setAtime(long atime) {
		this.atime = atime;
	}
	public long getCtime() {
		return ctime;
	}
	public void setCtime(long ctime) {
		this.ctime = ctime;
	}
	public long getMtime() {
		return mtime;
	}
	public void setMtime(long mtime) {
		this.mtime = mtime;
	}
	public int getIndirect() {
		return indirect;
	}
	public void setIndirect(int indirect) {
		this.indirect = indirect;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public int getFusedataNo() {
		return fusedataNo;
	}
	public void setFusedataNo(int fusedataNo) {
		this.fusedataNo = fusedataNo;
	}
}

class FileSystemChecker
{
	private String pathname = "FS/fusedata.";
	private String dirPathName = "FS";
	private Logger logger= Logger.getLogger(FileSystemChecker.class.getName());
	private ConsoleHandler handler = new ConsoleHandler();
	private SuperBlock superBlock=new SuperBlock();
	private DirBlock rootBlock = new DirBlock();
	private List<DirBlock> dirBlockList=new ArrayList<DirBlock>();
	private List<FileInode> fileInodeList=new ArrayList<FileInode>();
	private Map<Integer ,String[]> freeBlockMap=new HashMap<Integer, String[]>();
	private int noOfFiles; //number of files that are currently present in file system
	private final int maxBlockSize = 4096;
	
	public FileSystemChecker() throws IOException {
		initLogger();
		initSuperBlock();
		initRootBlock();
		initDirBlockList();
		initFileInodeList();
		initFreeBlockMap();
	}
	
	private void initSuperBlock() throws IOException
	{
		Path dirPath = Paths.get(dirPathName);
		if(Files.exists(dirPath))
		//check if FS directory exists or not
		{
			logger.config("Directory exist");
			//count number of files in the directory
			noOfFiles = (new File(dirPathName).list().length) -1;
		}
		Path filePath = Paths.get(pathname.concat("0"));
		if(Files.exists(filePath))
		{
			logger.config(pathname.concat("0")+" does exist");
			File file= new File(pathname.concat("0"));
			FileReader fr = new FileReader(file);
			BufferedReader br= new BufferedReader(fr);
			String fileContent = br.readLine();
			br.close();
			fr.close();
			fileContent = fileContent.replaceAll("\\s+",""); //remove any blank space
			fileContent = fileContent.replaceAll("\\{+","");
			fileContent = fileContent.replaceAll("\\}+","");
			//System.out.println("Final fileContent->"+fileContent);
			String[] fuse0 = fileContent.split(",");
			for(String s:fuse0)
			{
				String[] s1=s.split(":");
				for(int i=0;i<s1.length;i++)
				{
					if(s1[i].equals("creationTime"))
					{
						logger.info("creationTime="+s1[i+1]);
						superBlock.setCreationTime(Long.parseLong(s1[i+1]));
					}
					else if(s1[i].equals("mounted"))
					{
						logger.info("mounted="+s1[i+1]);
						superBlock.setMounted(Integer.parseInt(s1[i+1]));
					}
					else if(s1[i].equals("devId"))
					{
						logger.info("devId="+s1[i+1]);
						superBlock.setDevId(Integer.parseInt(s1[i+1]));
					}
					else if(s1[i].equals("freeStart"))
					{
						logger.info("freeStart="+s1[i+1]);
						superBlock.setFreeStart(Integer.parseInt(s1[i+1]));
					}
					else if(s1[i].equals("freeEnd"))
					{
						logger.info("freeEnd="+s1[i+1]);
						superBlock.setFreeEnd(Integer.parseInt(s1[i+1]));
					}
					else if(s1[i].equals("root"))
					{
						logger.info("root="+s1[i+1]);
						superBlock.setRoot(Integer.parseInt(s1[i+1]));
					}
					else if(s1[i].equals("maxBlocks"))
					{
						logger.info("maxBlocks="+s1[i+1]);
						superBlock.setMaxBlock(Integer.parseInt(s1[i+1]));
					}
				}				
			}
			//System.out.println("Super block-->"+superBlock.toString());
		}
		else if(Files.notExists(filePath))
		{
			logger.config(pathname.concat("0")+" does not exist");
		}
		//logger.exiting(FileSystemChecker.class.getName(), "initSuperBlock");
	}
	
	private void initRootBlock()throws IOException
	{
		Path filePath = Paths.get(pathname+superBlock.getRoot());
		List<FileNameInodeDict> inodeList=new ArrayList<FileNameInodeDict>();
		FileNameInodeDict inodeDictObj;
		if(Files.exists(filePath))
		{
			logger.config(pathname+superBlock.getRoot()+" does exist");
			boolean fileInodeReached = false;
			int mainCtr=0;
			File file= new File(pathname+superBlock.getRoot());
			if(file.length()>0)
			{
				FileReader fr = new FileReader(file);
				BufferedReader br= new BufferedReader(fr);
				String fileContent = br.readLine();
				br.close();
				fr.close();
				fileContent = fileContent.replaceAll("\\s+",""); //remove any blank space
				fileContent = fileContent.replaceAll("\\{+","");
				fileContent = fileContent.replaceAll("\\}+","");
				//System.out.println("root fileContent->"+fileContent);
				String[] fuse0 = fileContent.split(",");
				for(String s:fuse0)
				{
					mainCtr++;
					//System.out.println("s-->"+s);
					String[] s1=s.split(":");
					//System.out.println("s1 length="+s1.length);
					for(int i=0;i<s1.length;i++)
					{
						//System.out.println("i-->"+i+"\ts1-->"+s1[i]);
						if(fileInodeReached)
						{
							/*System.out.println("--------------------main_counter="+mainCtr+"----------------------------------------");
							int cnt=0;
							for(String s2:s1)
							{
								cnt++;
								System.out.println(cnt+"  s2-->  "+s2);
							}*/
							inodeDictObj=new FileNameInodeDict();
							inodeDictObj.setFileOrDir(s1[0]);
							inodeDictObj.setFileDirName(s1[1]);
							inodeDictObj.setBlockNo(Integer.parseInt(s1[2]));
							inodeList.add(inodeDictObj);
							i=s1.length;
							//System.out.println("--------------------------------------------------------------");
						}
						else if(s1[i].equals("size"))
						{
							logger.info("size="+s1[i+1]);
							rootBlock.setSize(Integer.parseInt(s1[i+1]));
						}
						else if(s1[i].equals("uid"))
						{
							logger.info("uid="+s1[i+1]);
							rootBlock.setUid(Integer.parseInt(s1[i+1]));
						}
						else if(s1[i].equals("gid"))
						{
							logger.info("gid="+s1[i+1]);
							rootBlock.setGid(Integer.parseInt(s1[i+1]));
						}
						else if(s1[i].equals("mode"))
						{
							logger.info("mode="+s1[i+1]);
							rootBlock.setMode(Integer.parseInt(s1[i+1]));
						}
						else if(s1[i].equals("atime"))
						{
							logger.info("atime="+s1[i+1]);
							rootBlock.setAtime(Long.parseLong(s1[i+1]));
						}
						else if(s1[i].equals("ctime"))
						{
							logger.info("ctime="+s1[i+1]);
							rootBlock.setCtime(Long.parseLong(s1[i+1]));
						}
						else if(s1[i].equals("mtime"))
						{
							logger.info("mtime="+s1[i+1]);
							rootBlock.setMtime(Long.parseLong(s1[i+1]));
						}
						else if(s1[i].equals("linkcount"))
						{
							logger.info("linkcount="+s1[i+1]);
							rootBlock.setLinkCount(Integer.parseInt(s1[i+1]));
						}
						else if(s1[i].equals("filename_to_inode_dict"))
						{
							//System.out.println("------main_ctr="+mainCtr);
							inodeDictObj=new FileNameInodeDict();
							inodeDictObj.setFileOrDir(s1[1]);
							inodeDictObj.setFileDirName(s1[2]);
							inodeDictObj.setBlockNo(Integer.parseInt(s1[3]));
							inodeList.add(inodeDictObj);
							fileInodeReached = true;
							i=s1.length;  //since value is v1:v2:v3, so to end loop after reading v1
						}
					}				
				}
				if(fileInodeReached)
				{
					rootBlock.setFuseDataNo(superBlock.getRoot());
					rootBlock.setInodeDictList(inodeList);
				}
				fileInodeReached = false;
				//System.out.println("Root block-->"+rootBlock.toString());
				
				/* Display value of array list
				 inodeList=new ArrayList<FileNameInodeDict>();
				inodeList= rootBlock.getInodeDictList();
				for(FileNameInodeDict fname:inodeList)
				{
					System.out.println("FileOrDir-->"+fname.getFileOrDir());
					System.out.println("FileDirName-->"+fname.getFileDirName());
					System.out.println("Block_no-->"+fname.getBlockNo());
					System.out.println("-----------------------------------------");
				}*/
			}
			
		}
		else if(Files.notExists(filePath))
		{
			logger.config(pathname+superBlock.getRoot()+" does not exist");
		}
		//logger.exiting(FileSystemChecker.class.getName(), "initRootBlock");
	}
	
	private void initDirBlockList()throws IOException
	{
		DirBlock dirBlockObj=null;
		FileNameInodeDict inodeDictObj=null;
		List<FileNameInodeDict> inodeList=new ArrayList<FileNameInodeDict>();
		boolean fileInodeReached=false;
		for(int j=superBlock.getRoot()+1;j<superBlock.getMaxBlock();j++)
		{
			dirBlockObj=new DirBlock();
			Path filePath = Paths.get(pathname+j);
			//System.out.println("Opening file:"+pathname+j);
			if(Files.exists(filePath))
			{
				//System.out.println("----Reading from file:"+pathname+j);
				File file=new File(pathname+j);
				if(file.length()>0)
				{
					FileReader fr = new FileReader(file);
					BufferedReader br= new BufferedReader(fr);
					String fileContent = br.readLine();
					br.close();
					fr.close();
					fileContent = fileContent.replaceAll("\\s+",""); //remove any blank space
					fileContent = fileContent.replaceAll("\\{+","");
					fileContent = fileContent.replaceAll("\\}+","");
					//System.out.println("root fileContent->"+fileContent);
					String[] fuse0 = fileContent.split(",");
					for(String s:fuse0)
					{
						//System.out.println("s-->"+s);
						String[] s1=s.split(":");
						//System.out.println("s1 length="+s1.length);
						for(int i=0;i<s1.length;i++)
						{
							//System.out.println("i-->"+i+"\ts1-->"+s1[i]);
							if(fileInodeReached)
							{
								/*System.out.println("--------------------main_counter="+mainCtr+"----------------------------------------");
								int cnt=0;
								for(String s2:s1)
								{
									cnt++;
									System.out.println(cnt+"  s2-->  "+s2);
								}*/
								//System.out.println("Inserting into inode_dict:"+s1[0]+"-"+s1[1]+"-"+s1[2]+"---for fusedata->"+j);
								inodeDictObj=new FileNameInodeDict();
								inodeDictObj.setFileOrDir(s1[0]);
								inodeDictObj.setFileDirName(s1[1]);
								inodeDictObj.setBlockNo(Integer.parseInt(s1[2]));
								inodeList.add(inodeDictObj);
								i=s1.length;
								//System.out.println("--------------------------------------------------------------");
							}
							else if(s1[i].equals("size"))
							{
								logger.info("size="+s1[i+1]);
								dirBlockObj.setSize(Integer.parseInt(s1[i+1]));
							}
							else if(s1[i].equals("uid"))
							{
								logger.info("uid="+s1[i+1]);
								dirBlockObj.setUid(Integer.parseInt(s1[i+1]));
							}
							else if(s1[i].equals("gid"))
							{
								logger.info("gid="+s1[i+1]);
								dirBlockObj.setGid(Integer.parseInt(s1[i+1]));
							}
							else if(s1[i].equals("mode"))
							{
								logger.info("mode="+s1[i+1]);
								dirBlockObj.setMode(Integer.parseInt(s1[i+1]));
							}
							else if(s1[i].equals("atime"))
							{
								logger.info("atime="+s1[i+1]);
								dirBlockObj.setAtime(Long.parseLong(s1[i+1]));
							}
							else if(s1[i].equals("ctime"))
							{
								logger.info("ctime="+s1[i+1]);
								dirBlockObj.setCtime(Long.parseLong(s1[i+1]));
							}
							else if(s1[i].equals("mtime"))
							{
								logger.info("mtime="+s1[i+1]);
								dirBlockObj.setMtime(Long.parseLong(s1[i+1]));
							}
							else if(s1[i].equals("linkcount"))
							{
								logger.info("linkcount="+s1[i+1]);
								dirBlockObj.setLinkCount(Integer.parseInt(s1[i+1]));
							}
							else if(s1[i].equals("filename_to_inode_dict"))
							{
								//System.out.println("------main_ctr="+mainCtr);
								//System.out.println("Inserting into inode_dict:"+s1[1]+"-"+s1[2]+"-"+s1[3]+"---for fusedata->"+j);
								inodeList=new ArrayList<FileNameInodeDict>();
								inodeDictObj=new FileNameInodeDict();
								inodeDictObj.setFileOrDir(s1[1]);
								inodeDictObj.setFileDirName(s1[2]);
								inodeDictObj.setBlockNo(Integer.parseInt(s1[3]));
								inodeList.add(inodeDictObj);
								fileInodeReached = true;
								i=s1.length;  //since value is v1:v2:v3, so to end loop after reading v1
							}
						}				
					}
					if(fileInodeReached) //if file_name_to_inode_dict json structure is found, then only add in list
					{
						dirBlockObj.setFuseDataNo(j);
						//System.out.println("adding to dirBlockList-->"+dirBlockObj.getFuseDataNo());
						dirBlockObj.setInodeDictList(inodeList);
						dirBlockList.add(dirBlockObj);
					}
					fileInodeReached = false;
				}
				
			}
		}
		/*System.out.println("-----------------------content of dir block list------------------:"+dirBlockList.size()+" no of files:"+noOfFiles);
		for(DirBlock block:dirBlockList)
		{
			System.out.println("------------------------\n"+block);
			if(null != block.getInodeDictList())
			{
				for(FileNameInodeDict dict:block.getInodeDictList())
				{
					System.out.println(dict);
				}
				System.out.println("----------------------------------");
			}
		}*/
	}
	
	private void initFileInodeList()throws IOException
	{
		FileInode fileInodeObj=null;
		boolean fileIndirectReached,fileInodeReached;
		for(int j=superBlock.getRoot()+1;j<superBlock.getMaxBlock();j++)
		{		
			Path fileBlockPath=Paths.get(pathname+j);
			if(Files.exists(fileBlockPath))
			{
				File file=new File(pathname+j);
				if(null!=file && file.length()>0)
				{
					//System.out.println("Reading from file->"+pathname+j);
					fileIndirectReached=false;
					fileInodeReached=false;
					fileInodeObj=new FileInode();
					FileReader fr=new FileReader(file);
					BufferedReader br=new BufferedReader(fr);
					String fileContent=br.readLine();
					br.close();
					fr.close();
					fileContent=fileContent.replaceAll("\\{+","");
					fileContent=fileContent.replaceAll("\\}+","");
					String[] fuse0 = fileContent.split(", ");
					//System.out.println("----FileContent:"+fileContent);
					for(String s:fuse0)
					{
						//System.out.println("s-->"+s);
						String[] s1=s.split(":");
						//System.out.println("s1 length="+s1.length);
						for(int i=0;i<s1.length;i++)
						{
							//System.out.println("i-->"+i+"\ts1-->"+s1[i]);
							if(s1[i].equals("size"))
							{
								logger.info("size="+s1[i+1]);
								fileInodeObj.setSize(Integer.parseInt(s1[i+1]));
							}
							else if(s1[i].equals("uid"))
							{
								logger.info("uid="+s1[i+1]);
								fileInodeObj.setUid(Integer.parseInt(s1[i+1]));
							}
							else if(s1[i].equals("gid"))
							{
								logger.info("gid="+s1[i+1]);
								fileInodeObj.setGid(Integer.parseInt(s1[i+1]));
							}
							else if(s1[i].equals("mode"))
							{
								logger.info("mode="+s1[i+1]);
								fileInodeObj.setMode(Integer.parseInt(s1[i+1]));
							}
							else if(s1[i].equals("atime"))
							{
								logger.info("atime="+s1[i+1]);
								fileInodeObj.setAtime(Long.parseLong(s1[i+1]));
							}
							else if(s1[i].equals("ctime"))
							{
								logger.info("ctime="+s1[i+1]);
								fileInodeObj.setCtime(Long.parseLong(s1[i+1]));
							}
							else if(s1[i].equals("mtime"))
							{
								logger.info("mtime="+s1[i+1]);
								fileInodeObj.setMtime(Long.parseLong(s1[i+1]));
							}
							else if(s1[i].equals("linkcount"))
							{
								logger.info("linkcount="+s1[i+1]);
								fileInodeObj.setLinkCount(Integer.parseInt(s1[i+1]));
							}
							else if(s1[i].equals("indirect"))
							/*
							 * Since json structure is 'indirect:value location:value'
							 * when s1[i]->indirect, then s-> indirect:value location:value
							 * so split 's' according to ' ' and store into s2[]
							 * then s2[0]->indirect:value   s2[1]->location:value
							 * now iterate over s2[] and split according to ':'
							 */
							{
								logger.info("indirect="+s1[i+1]);
								String[] s2=s.split(" ");
								for(String s3:s2)
								{
									String[] s4=s3.split(":");
									for(int k=0;k<s4.length;k++)
									{
										if(s4[k].equals("indirect"))
										{
											fileInodeObj.setIndirect(Integer.parseInt(s4[k+1]));
										}
										else if(s4[k].equals("location"))
										{
											fileInodeObj.setLocation(Integer.parseInt(s4[k+1]));
										}
									}
								}
								fileIndirectReached = true;
								//fileInodeObj.setIndirect(Integer.parseInt(s1[i+1]));
							}
							else if(s1[i].equals("filename_to_inode_dict"))
							{
								//System.out.println("------main_ctr="+mainCtr);
								fileInodeReached=true;
								break;
							}
						}		
						if(fileInodeReached || fileIndirectReached)
						//break middle loop if file_name_to_inode json structure is reached or indirect json value is read
						{
							break;
						}
					}
					if(fileIndirectReached)
					{
						//System.out.println("***************Inserting into fileInodeList-->"+pathname+j);
						fileInodeObj.setFusedataNo(j);
						fileInodeList.add(fileInodeObj);	
					}
				}
			}
		}
		/*System.out.println("--------------file block values------------------");
		if(null!=fileInodeList && fileInodeList.size()>0)
		{
			for(FileInode fileInode:fileInodeList)
			{
				System.out.println(fileInode);
				System.out.println("-------------------");
			}
		}*/
	}
	
	private void initFreeBlockMap()throws IOException
	{
		for(int i=superBlock.getFreeStart();i<=superBlock.getFreeEnd();i++)
		{
			Path fBlkPath = Paths.get(pathname+i);
			if(Files.exists(fBlkPath))
			{
				//System.out.println("checking--->"+pathname+i);
				File freeBlkFile=new File(pathname+i);
				FileReader fr = new FileReader(freeBlkFile);
				BufferedReader br=new BufferedReader(fr);
				String freeBlk = br.readLine();
				fr.close();
				br.close();
				freeBlk=freeBlk.replaceAll("\\s+","");
				String[] freeBlkArr = freeBlk.split(",");
				freeBlockMap.put(i, freeBlkArr);
			}
		}
	}
	
	private void initLogger()
	{
		logger.setUseParentHandlers(false); // prevent logger from displaying msg twice on console
		logger.addHandler(handler);
		logger.setLevel(Level.FINE);
		
		handler.setLevel(Level.SEVERE);
		
		//handler.setLevel(Level.FINE);
		//handler.setLevel(Level.INFO);
	}
	
	private void checkDeviceID() throws IOException 
	{		
		if(null != superBlock && superBlock.getDevId()!=20)
		{
			logger.info("Dev id is incorrect:"+superBlock.getDevId());
			System.out.println("Incorrect device id found in file\n"+pathname.concat("0")+" --> devId:"+superBlock.getDevId());
			System.out.println("----------------------Exiting checker------------------------------------------------");
			System.exit(0); //program will terminate if device id is other then 20
		}
		else if(null != superBlock && superBlock.getDevId()==20)
		{
			logger.config("Device id is correct "+superBlock.getDevId());
		}
		logger.exiting(FileSystemChecker.class.getName(), "checkDeviceID");
	}
	
	private void checkTime(DirBlock dirBlockObj,long currentTime)
	//check for a directory if all timestamp are in past
	{
		if(null!=dirBlockObj)
		{
			if(dirBlockObj.getAtime()>currentTime)
			{
				System.out.println("atime is greater then current time for\n"+pathname+dirBlockObj.getFuseDataNo()+
						" --> atime:"+superBlock.getCreationTime()+"  currentTime:"+currentTime);
				System.out.println("----------------------------------------------------------------------");
			}
			if(dirBlockObj.getCtime()>currentTime)
			{
				System.out.println("ctime is greater then current time for\n"+pathname+dirBlockObj.getFuseDataNo()+
						" --> ctime:"+superBlock.getCreationTime()+"  currentTime:"+currentTime);
				System.out.println("----------------------------------------------------------------------");
			}
			if(dirBlockObj.getMtime()>currentTime)
			{
				System.out.println("mtime is greater then current time for\n"+pathname+dirBlockObj.getFuseDataNo()+
						" --> mtime:"+superBlock.getCreationTime()+"  currentTime:"+currentTime);
				System.out.println("----------------------------------------------------------------------");
			}
		}
	}
	
	private void checkTime()
	{
		long currentTime=System.currentTimeMillis()/1000;
		//check for super block
		if(null!=superBlock)
		{
			if(superBlock.getCreationTime() > currentTime)
			{
				System.out.println("creationTime is greater then current time for\n"+
						pathname+"0 --> creationTime:"+superBlock.getCreationTime()+"  currentTime:"+currentTime);
				System.out.println("----------------------------------------------------------------------");
			}
		}
		//check root block
		checkTime(rootBlock,currentTime);
		//check for all other directories
		if(!dirBlockList.isEmpty())
		{
			for(DirBlock dirBlockObj:dirBlockList)
				checkTime(dirBlockObj,currentTime);
		}
		//check for file inode
		if(null!=fileInodeList && fileInodeList.size()>0)
		{
			for(FileInode fileInodeObj:fileInodeList)
			{
				if(fileInodeObj.getAtime()>currentTime)
				{
					System.out.println("atime is greater then current time for\n"+pathname+fileInodeObj.getFusedataNo()+
							" --> atime:"+superBlock.getCreationTime()+"  currentTime:"+currentTime);
					System.out.println("----------------------------------------------------------------------");
				}
				if(fileInodeObj.getCtime()>currentTime)
				{
					System.out.println("ctime is greater then current time for\n"+pathname+fileInodeObj.getFusedataNo()+
							" --> ctime:"+superBlock.getCreationTime()+"  currentTime:"+currentTime);
					System.out.println("----------------------------------------------------------------------");
				}
				if(fileInodeObj.getMtime()>currentTime)
				{
					System.out.println("mtime is greater then current time for\n"+pathname+fileInodeObj.getFusedataNo()+
							" --> mtime:"+superBlock.getCreationTime()+"  currentTime:"+currentTime);
					System.out.println("----------------------------------------------------------------------");
				}
			}
		}
	}
	
	private void checkAllFreeBlock()
	/*
	 * This functions checks if all 10,000 block number are covered in free block list, if that block if empty
	 */
	{
		boolean isFreeBlkPresent; //flag to indicate if blockNo is present in free_block array
		for(int blockNo=superBlock.getRoot()+1;blockNo<superBlock.getMaxBlock();blockNo++)
		{
			Path blockPath=Paths.get(pathname+blockNo);
			if(Files.exists(blockPath))
			//check if that file exist
			{
				File file=new File(pathname+blockNo);
				if(file.length()==0)
				//check if file has any content
				{
					if(!freeBlockMap.isEmpty())
					{
						/*
						 * Retrieve free_block array from map for that block number
						 * i.e. If fusedata.31 is found empty, then retrieve free_block array 
						 * from map at key((floor(blockNo/400))+1) i.e->key(1)
						 */
						isFreeBlkPresent=false;
						String[] freeBlockArr=freeBlockMap.get((int)(Math.floor(blockNo/400)+1));
						for(String freeBlock:freeBlockArr)
						//from free_block array, check if that blockNo if present in it
						{
							if(blockNo==Integer.parseInt(freeBlock))
							{
								isFreeBlkPresent=true;
								break;
							}
						}
						if(!isFreeBlkPresent)
						{
							System.out.println("Block number:"+blockNo+" is free but not present in free block list");
							System.out.println("----------------------------------------------------------------------");
						}
					}
				}
			}
			else
			//if that file does not exist, then also we have to check if that block_no is present in free block list	
			{
				isFreeBlkPresent=false;
				String[] freeBlockArr=freeBlockMap.get((int)(Math.floor(blockNo/400)+1));
				for(String freeBlock:freeBlockArr)
				//from free_block array, check if that blockNo if present in it
				{
					if(blockNo==Integer.parseInt(freeBlock))
					{
						isFreeBlkPresent=true;
						break;
					}
				}
				if(!isFreeBlkPresent)
				{
					System.out.println("Block number:"+blockNo+" is missing from free block list of:"+pathname+(int)(Math.floor(blockNo/400)+1));
					System.out.println("----------------------------------------------------------------------");
				}
			}
		}
	}
	
	private void validateFreeBlock()throws IOException
	{
		//Step3.a->making sure that free block list contains all free block
		checkAllFreeBlock();
		//Step3.b->making sure there are no file/dir in the block_no specified by free block list
		if(!freeBlockMap.isEmpty())
		{
			for(int i:freeBlockMap.keySet())
			{
				String[] freeBlkArr=freeBlockMap.get(i);
				for(String blockNo:freeBlkArr)
				{
					Path freePath = Paths.get(pathname+blockNo);
					if(Files.exists(freePath))
					//if that file exist, check for content in file	
					{
						//System.out.println(pathname+finame+" does exist");
						File freeBlkFile = new File(pathname+blockNo);
						if(freeBlkFile.length()>0)
						{
							System.out.println("Error in free block list\n"+pathname+i+" --> block_number:"+blockNo+
									" is not empty, still present in free block list");
							System.out.println("----------------------------------------------------------------------");
						}
					}
					else if(Files.notExists(freePath))
					{
						//System.out.println(pathname+finame+" does not exist");
					}
				}				
			}
		}
	}
	
	private void validateDirLinkCount()
	{
		//check for root directory
		if(rootBlock.getLinkCount() != rootBlock.getInodeDictList().size())
		{
			System.out.println("Link count not matching for --> "+pathname+rootBlock.getFuseDataNo());
			System.out.println("----------------------------------------------------------------------");
		}
		//check for all other directories
		if(null != dirBlockList)
		{
			if(!dirBlockList.isEmpty())
			{
				for(DirBlock dirBlock:dirBlockList)
				{
					if(dirBlock.getLinkCount() != dirBlock.getInodeDictList().size())
					{
						System.out.println("Link count not matching for --> "+pathname+dirBlock.getFuseDataNo());
						System.out.println("----------------------------------------------------------------------");
					}
				}
			}
		}
	}
	
	private void validateRootDir()
	//check if root block has valid entry for . and ..
	{
		boolean isDotPresent=false,isDotDotPresent=false;
		if(!rootBlock.getInodeDictList().isEmpty())
		{
			for(FileNameInodeDict iNodeObj:rootBlock.getInodeDictList())
			{
				if(iNodeObj.getFileOrDir().equalsIgnoreCase("d")) //check only for directory entry
				{
					if(iNodeObj.getFileDirName().equals("."))
					{
						isDotPresent=true;
						if(iNodeObj.getBlockNo()==superBlock.getRoot())
						{
							continue;
						}
						else
						{
							System.out.println("Invalid block number for '.' in root block\noriginal_value:"+iNodeObj.getBlockNo()+
									"   correct_value:"+superBlock.getRoot());
							System.out.println("----------------------------------------------------------------------");
						}
					}
					else if(iNodeObj.getFileDirName().equals(".."))
					{
						isDotDotPresent=true;
						if(iNodeObj.getBlockNo()==superBlock.getRoot())
						{
							continue;
						}
						else
						{
							System.out.println("Invalid block number for '..' in root block\noriginal_value:"+iNodeObj.getBlockNo()+
									"   correct_value:"+superBlock.getRoot());
							System.out.println("----------------------------------------------------------------------");
						}
					}
				}
			}
			if(!isDotPresent)
			{
				if(!isDotDotPresent)
				{
					System.out.println("'.' & '..' entry missing for root block");
					System.out.println("----------------------------------------------------------------------");
				}
				else
				{
					System.out.println("'.' entry missing for root block");
					System.out.println("----------------------------------------------------------------------");
				}
			}
			else if(!isDotDotPresent)
			{
				System.out.println("'..' entry missing for root block");
				System.out.println("----------------------------------------------------------------------");
			}
		}
	}
	
	private boolean validateDirParent(int parentBlockNo,int currentBlockNo)
	/*
	 * This function checks if for a particular directory,the entry in file_name_to_inode for '.' and '..'
	 * have valid parent
	 * i.e. If for fusedata.30-> {d:.:26,d:..:26}
	 * then check in fusedata.26 for {d:string:30}
	 */
	{
		if(superBlock.getRoot()==parentBlockNo)
		//if parent block no was a root dir
		{
			if(!rootBlock.getInodeDictList().isEmpty())
			{
				for(FileNameInodeDict iNodeObj:rootBlock.getInodeDictList())
				{
					if(iNodeObj.getBlockNo()==currentBlockNo) //check only for directory entry
					{
						if(iNodeObj.getFileOrDir().equalsIgnoreCase("d"))
						{
							return true;
						}
					}
				}
			}
		}
		else
		//if parent block number was other then root dir
		{
			if(!dirBlockList.isEmpty())
			{
				for(DirBlock dirBlockObj:dirBlockList)
				{
					if(dirBlockObj.getFuseDataNo()==parentBlockNo)
					//extract from dir_block_list only that directory mentioned in parent_block_no
					{
						if(null!=dirBlockObj.getInodeDictList() && dirBlockObj.getInodeDictList().size()>0)
						{
							for(FileNameInodeDict iNodeDict:dirBlockObj.getInodeDictList())
							{
								if(iNodeDict.getBlockNo()==currentBlockNo)
								{
									if(iNodeDict.getFileOrDir().equalsIgnoreCase("d"))
										return true;
								}
							}
						}
					}
					else
						continue;
				}
			}
		}
		return false;
	}
	
	private int findCorrectParent(int currentBlockNo)
	//this function determines the correct parent directory for given block number
	{
		//System.out.println("Searching for currentBlockNo-->"+currentBlockNo);
		if(!dirBlockList.isEmpty())
		{
			for(DirBlock dirBlockObj:dirBlockList)
			{
				if(null!=dirBlockObj.getInodeDictList() && dirBlockObj.getInodeDictList().size()>0)
				{
					for(FileNameInodeDict iNodeDict:dirBlockObj.getInodeDictList())
					{
						//System.out.println("Opening from dirBlockNo-->"+dirBlockObj.getFuseDataNo()+" inode_dict block no-->"+iNodeDict.getBlockNo());
						if(iNodeDict.getBlockNo()==currentBlockNo)
						{
							//check if inode:blockNo is not corresponding entry for '.' or '..'
							if(iNodeDict.getFileOrDir().equalsIgnoreCase("d") && !(iNodeDict.getFileDirName().equals(".") || iNodeDict.getFileDirName().equals("..")))
								return dirBlockObj.getFuseDataNo();
						}
					}
				}
			}
		}
		return 0;
	}
	
	private void validateEachDir()
	{
		//validate root block
		validateRootDir();
		//validate remaining directory block
		if(dirBlockList.size()>0)
		{
			boolean isDotPresent,isDotDotPresent;
			for(DirBlock dirBlock:dirBlockList)
			{
				isDotPresent=false;
				isDotDotPresent=false;
				if(null!=dirBlock.getInodeDictList() && dirBlock.getInodeDictList().size()>0)
				{
					for(FileNameInodeDict iNodeObj:dirBlock.getInodeDictList())
					{
						if(iNodeObj.getFileOrDir().equalsIgnoreCase("d")) //check only for directory entry
						{
							if(iNodeObj.getFileDirName().equals("."))
							{
								isDotPresent=true;
								//if(validateDirParent(iNodeObj.getBlockNo(),dirBlock.getFuseDataNo()))
								if(iNodeObj.getBlockNo()==dirBlock.getFuseDataNo())
								//validate if entry for '.' is current block_no
								{
									continue;
								}
								else
								{
									System.out.println("Invalid block number for '.' in "+pathname+dirBlock.getFuseDataNo()+
											"\noriginal_value:"+iNodeObj.getBlockNo()+"   correct_value:"+dirBlock.getFuseDataNo());
									System.out.println("----------------------------------------------------------------------");
								}
							}
							else if(iNodeObj.getFileDirName().equals(".."))
							{
								isDotDotPresent=true;
								if(validateDirParent(iNodeObj.getBlockNo(),dirBlock.getFuseDataNo()))
								//validate if parent dir has an entry for current dir
								{
									continue;
								}
								else
								{
									System.out.println("Invalid block number for '..' in "+pathname+dirBlock.getFuseDataNo()+
											"\noriginal_value:"+iNodeObj.getBlockNo()+"  correct_value:"+findCorrectParent(dirBlock.getFuseDataNo()));
									System.out.println("----------------------------------------------------------------------");
								}
							}
						}
					}
					if(!isDotPresent)
					{
						if(!isDotDotPresent)
						{
							System.out.println("'.' & '..' entry missing for "+pathname+dirBlock.getFuseDataNo());
							System.out.println("----------------------------------------------------------------------");
						}
						else
						{
							System.out.println("'.' entry missing for "+pathname+dirBlock.getFuseDataNo());
							System.out.println("----------------------------------------------------------------------");
						}
					}
					else if(!isDotDotPresent)
					{
						System.out.println("'..' entry missing for root block");
						System.out.println("----------------------------------------------------------------------");
					}
				}
			}
		}
		
	}
	//----------------------------logic taken from stackover flow--START--------------------------
	private boolean isNumeric(String str)
	//determines if input string is number or not
	{
		NumberFormat format=NumberFormat.getInstance();
		ParsePosition position=new ParsePosition(0);
		format.parse(str, position);
		return str.length()==position.getIndex();
	}
	//----------------------------logic taken from stackover flow--END--------------------------
	
	private void validateInodeIndirect()throws IOException
	{
		if(null!= fileInodeList && !fileInodeList.isEmpty())
		{
			for(FileInode fileInodeObj: fileInodeList)
			{
				if(fileInodeObj.getLocation()>0)
				{
					Path filePath= Paths.get(pathname+fileInodeObj.getLocation());
					if(Files.exists(filePath))
					{
						boolean isStringFlag=false; //flag to indicate if block reading is an index block of array or other string 
						File file=new File(pathname+fileInodeObj.getLocation());
						FileReader fr=new FileReader(file);
						BufferedReader br=new BufferedReader(fr);
						String fileContent=br.readLine();
						fr.close();
						br.close();
						//fileContent=fileContent.replaceAll("\\s+","");
						String[] indexBlockArr = initInodeIndexBlockArr(fileInodeObj.getLocation());
						for(String s:indexBlockArr)
						{
							isStringFlag=false;
							if(!isNumeric(s))
							{
								isStringFlag=true;
								break;
							}
						}
						if(!isStringFlag)
						//if value in index block is array of integer, then indirect for that file inode should be 1
						{
							if(fileInodeObj.getIndirect() != 1)
							{
								System.out.println("File--> "+pathname+fileInodeObj.getFusedataNo()+" has indirect:"+
										fileInodeObj.getIndirect()+" whereas location contains--> "+fileContent);
								System.out.println("----------------------------------------------------------------------");
							}
						}
					}
				}
			}
		}
	}
	
	private boolean fileExists(int blockNo)
	{
		Path path=Paths.get(pathname+blockNo);
		return Files.exists(path);
	}
	
	private String readFile(int blockNo)throws IOException
	//reads the content of the file from a given block number
	{
		if(fileExists(blockNo))
		{
			File file=new File(pathname+blockNo);
			FileReader fr=new FileReader(file);
			BufferedReader br=new BufferedReader(fr);
			String str=br.readLine();
			fr.close();
			br.close();
			return str;
		}
		return null;
	}
	
	private String[] initInodeIndexBlockArr(int location) throws IOException
	{
		String fileContent=readFile(location);
		if(null!= fileContent)
		{
			String tempFileContent=fileContent.replaceAll("\\s+","");
			//logic to determine if index if separated by ',' or ' '
			if(tempFileContent.contains(","))
			{
				return tempFileContent.split(",");
			}
			else
				return fileContent.split(" ");
		}
		return null;
	}
	
	private void validateBlkPntrSize()throws IOException
	/*
	 * For every file_inode entry, this function checks for three possibilities
	 */
	{
		if(!fileInodeList.isEmpty())
		{
			for(FileInode fileInodeObj:fileInodeList)
			{
				//Step 7.a--> check that size is less then maxBlockSize, if indirect ==0
				/*if(fileInodeObj.getSize()>0 && (fileInodeObj.getIndirect()!=0 && fileInodeObj.getSize()<maxBlockSize))
				{
					System.out.println("Error in "+pathname+fileInodeObj.getFusedataNo()+" -> indirect:"+fileInodeObj.getIndirect()+"\nindirect should be 0 "+
							"as fileSize:"+fileInodeObj.getSize()+" is less then block_size:"+maxBlockSize);
					System.out.println("----------------------------------------------------------------------");
				}*/
				if(fileInodeObj.getSize()>0 && (fileInodeObj.getIndirect()==0 && fileInodeObj.getSize()>maxBlockSize))
				//if indirect is 0, then file_size should be less then block size
				{
					System.out.println("Error in "+pathname+fileInodeObj.getFusedataNo()+" -> indirect:"+fileInodeObj.getIndirect()+"\n"+
							"fileSize:"+fileInodeObj.getSize()+" should be less then block_size:"+maxBlockSize);
					System.out.println("----------------------------------------------------------------------");
				}
				if(fileInodeObj.getIndirect()!=0)
				{
					String[] inodeIndexBlkArr=initInodeIndexBlockArr(fileInodeObj.getLocation());
					if(null!=inodeIndexBlkArr)
					{
						//Step 7.b--> size should be less then blockSixe * length_location_array if indirect is 1
						if(fileInodeObj.getSize() > Math.multiplyExact(maxBlockSize,inodeIndexBlkArr.length))
						{
							System.out.println("Error in "+pathname+fileInodeObj.getFusedataNo()+" -> indirect:"+fileInodeObj.getIndirect()+"\n"+
									"fileSize:"+fileInodeObj.getSize()+" should be less then block_size*location_array_len:"+maxBlockSize+"*"+inodeIndexBlkArr.length
									+"\nor assign more block to this file");
							System.out.println("----------------------------------------------------------------------");
						}
						//Step 7.c--> size should be greater then [blockSixe * (length_location_array - 1)] if indirect is 1
						if(fileInodeObj.getSize() < Math.multiplyExact(maxBlockSize,inodeIndexBlkArr.length-1))
						{
							System.out.println("Error in "+pathname+fileInodeObj.getFusedataNo()+" -> indirect:"+fileInodeObj.getIndirect()+"\n"+
									"fileSize:"+fileInodeObj.getSize()+" should be greater then block_size*(location_array_len - 1):"+maxBlockSize+"*"+
									(inodeIndexBlkArr.length-1)+"\nor assign less block to this file");
							System.out.println("----------------------------------------------------------------------");
						}
					}
				}
			}
		}
	}
	
	void fileCheckerFunc() throws IOException
	{		
		//int noOfFiles=0;
		Path dirPath = Paths.get(dirPathName);
		if(Files.exists(dirPath))
		//check if FS directory exists or not
		{
			logger.config("Directory exist");
			//count number of files in the directory
			//noOfFiles = (new File(dirPathName).list().length) -1;
			logger.info("Number of files= "+noOfFiles);
			
			//Step.1-> Check if device Id is correct in root directory
			checkDeviceID();
			//Step.2-> Check if all times are in past
			checkTime();
			//Step.3-> Validate free block list
			validateFreeBlock();
			//Step.4-> Check if each dir contains . and ..
			validateEachDir();
			//Step.5-> Validate each dir link_count = no_links in file_name_to_inode_dict
			validateDirLinkCount();
			//Step.6-> If data at location is array, then indirect should be 1
			validateInodeIndirect();
			//Step.7-> Check if size is valid for number of block pointer in location array
			validateBlkPntrSize();
		}
		else if(Files.notExists(dirPath))
		{
			logger.config("Directory does not exist");
		}
	}
}