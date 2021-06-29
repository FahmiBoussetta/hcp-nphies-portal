import React, { useEffect } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './cov-eli-resp-error-messages.reducer';

export const CovEliRespErrorMessagesDeleteDialog = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const covEliRespErrorMessagesEntity = useAppSelector(state => state.covEliRespErrorMessages.entity);
  const updateSuccess = useAppSelector(state => state.covEliRespErrorMessages.updateSuccess);

  const handleClose = () => {
    props.history.push('/cov-eli-resp-error-messages');
  };

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(covEliRespErrorMessagesEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="covEliRespErrorMessagesDeleteDialogHeading">
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="hcpNphiesPortalApp.covEliRespErrorMessages.delete.question">
        <Translate
          contentKey="hcpNphiesPortalApp.covEliRespErrorMessages.delete.question"
          interpolate={{ id: covEliRespErrorMessagesEntity.id }}
        >
          Are you sure you want to delete this CovEliRespErrorMessages?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-covEliRespErrorMessages" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default CovEliRespErrorMessagesDeleteDialog;
