#ifndef OMG_TDDS_CORE_INSTANCE_HANDLE_HPP_
#define OMG_TDDS_CORE_INSTANCE_HANDLE_HPP_

/* Copyright 2010, Object Management Group, Inc.
 * Copyright 2010, PrismTech, Corp.
 * Copyright 2010, Real-Time Innovations, Inc.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include <dds/core/types.hpp>
#include <dds/core/Value.hpp>


namespace dds { namespace core {

   template <typename DELEGATE>
   class TInstanceHandle : public dds::core::Value<DELEGATE> {
   public:
      TInstanceHandle() { }

      template <typename ARG0>
      TInstanceHandle(const ARG0& arg0) 
      : dds::core::Value<DELEGATE>(arg0) { }

      TInstanceHandle(const dds::core::null_type& nullHandle)
      : dds::core::Value<DELEGATE>(nullHandle) { }

      TInstanceHandle(const TInstanceHandle& other)
      : dds::core::Value<DELEGATE>(other.delegate())
        { }

      ~TInstanceHandle() { }

   public:
      TInstanceHandle& operator=(const TInstanceHandle& that) {
         if (this != &that) {
            this->delegate() = that.delegate();
         }
         return *this;
      }

      bool operator==(const TInstanceHandle& that) {
         return this->delegate() == that.delegate();
      }


   public:
      static const TInstanceHandle nil() {
         static TInstanceHandle nil_handle;
         return nil_handle;
      }


      bool is_nil() const {
         return this->delegate().is_nil();
      }
   };

} }

#endif // !defined(OMG_TDDS_CORE_INSTANCE_HANDLE_HPP_)
