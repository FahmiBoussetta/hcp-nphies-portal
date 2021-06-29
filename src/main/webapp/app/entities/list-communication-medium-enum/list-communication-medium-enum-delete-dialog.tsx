import React, { useEffect } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './list-communication-medium-enum.reducer';

export const ListCommunicationMediumEnumDeleteDialog = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const listCommunicationMediumEnumEntity = useAppSelector(state => state.listCommunicationMediumEnum.entity);
  const updateSuccess = useAppSelector(state => state.listCommunicationMediumEnum.updateSuccess);

  const handleClose = () => {
    props.history.push('/list-communication-medium-enum');
  };

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(listCommunicationMediumEnumEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="listCommunicationMediumEnumDeleteDialogHeading">
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="hcpNphiesPortalApp.listCommunicationMediumEnum.delete.question">
        <Translate
          contentKey="hcpNphiesPortalApp.listCommunicationMediumEnum.delete.question"
          interpolate={{ id: listCommunicationMediumEnumEntity.id }}
        >
          Are you sure you want to delete this ListCommunicationMediumEnum?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button
          id="jhi-confirm-delete-listCommunicationMediumEnum"
          data-cy="entityConfirmDeleteButton"
          color="danger"
          onClick={confirmDelete}
        >
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default ListCommunicationMediumEnumDeleteDialog;
