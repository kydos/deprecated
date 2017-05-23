
                            DISTRIBUTED FILE SYSTEM

This example shows how DDS can be used to implement a distributed file-system
supporting the most commonly required operations, such as:

    - Create (for file and directory)
    - Write
    - Read (for file and directory)
    - Delete (for files)


The structure of the file-system is captured by means of DDS partitions,
as such, a file with fully-qualified-path:

    /some/place/on/hdd/myFile.xyz

is mapped into the following combination of Partition/Topic:

DDS Partition:
    "/some/place/on/hdd"

Topic:
    DDSFile("myFile.xyz", data)


Where the data type for the File topic is the following:


    typedef sequence<octet> OctetSequence_t;

    struct DDSFile {
        string name;
        OctetSequence_t data;
    };
    #pragma keylist DDSFile name


NOTE:
  Another way of representing the DDSFile could have been
  to define it as follows:

    string DDSFile {
        string name;
        string path;
        OctetSequence_t data;
    };
    #pragma keylist DDSFile name path

  The disadvantage of this representation is that it des not
  allow the use of partition to segregate the content of directories.
  As a result, when subscribing the the DDSFile topic one would
  immediately match the content of the whole file-system -- unless
  content-filtered subscriptions would be used.

  That said, the attentive reader might wonder why on the DDSFile
  type above we are not keeping an attribute for the path. This
  is to avoid anomalies/inconsistencies by design when implementing
  file move across directories. The information about the path
  is kept as part of the Partition QoS Policy.



                     MAPPING FILE SYSTEM OPERATIONS TO DDS

The traditional operations on files can be mapped to DDS operations as
follows (note that there are some caveats that I'll explain later):


    CREATE

The Create operation for file consist simply in creating a topic instance
having as key the name of the file to be created.


    WRITE

The Write operation for a file simply maps to the DDS write


     READ

The Read operation for directory maps to a DDS read for the
DDSFile topic on the partition representing the base dir of the
file. On the other hand a read for a specific file maps to the
read_instance operation.


     DELETE

The Delete operation for files can be implemented via the "dispose"
operation on topics. However this requires discipline as its semantic
will be that of a file-system delete only after all the writer currently
alive for a given instance will have invoked the dispose operation.

As such, the implementation of the file-system should ensure that after
a write for a given file, the DDS datawriter unregisters right away
the instance it just wrote.


                TRANSIENT vs. PERSISTENT FILE SYSTEM

The Durability QoS can be used to control whether the file-system,
or even some specific files need to be transient or persistent.
For instance something representing /tmp would be transient, while
other directory whose content needs to be available across restarts
need to be persistent.


                        IMPLEMENTATION NOTES

The code included on the example does not implement yet all of these
features described above. The full implementation will come soon --
as soon as I get an hour to finish this up.


                             CONTACT

For any questions, comments, and contributions contact:

    Angelo Corsaro <angelo at icorsaro dot net>