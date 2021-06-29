import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICommunication } from 'app/shared/model/communication.model';
import { getEntities as getCommunications } from 'app/entities/communication/communication.reducer';
import { getEntity, updateEntity, createEntity, reset } from './list-communication-reason-enum.reducer';
import { IListCommunicationReasonEnum } from 'app/shared/model/list-communication-reason-enum.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ListCommunicationReasonEnumUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const communications = useAppSelector(state => state.communication.entities);
  const listCommunicationReasonEnumEntity = useAppSelector(state => state.listCommunicationReasonEnum.entity);
  const loading = useAppSelector(state => state.listCommunicationReasonEnum.loading);
  const updating = useAppSelector(state => state.listCommunicationReasonEnum.updating);
  const updateSuccess = useAppSelector(state => state.listCommunicationReasonEnum.updateSuccess);

  const handleClose = () => {
    props.history.push('/list-communication-reason-enum');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCommunications({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...listCommunicationReasonEnumEntity,
      ...values,
      communication: communications.find(it => it.id.toString() === values.communicationId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...listCommunicationReasonEnumEntity,
          cr: 'Missing_info',
          communicationId: listCommunicationReasonEnumEntity?.communication?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="hcpNphiesPortalApp.listCommunicationReasonEnum.home.createOrEditLabel"
            data-cy="ListCommunicationReasonEnumCreateUpdateHeading"
          >
            <Translate contentKey="hcpNphiesPortalApp.listCommunicationReasonEnum.home.createOrEditLabel">
              Create or edit a ListCommunicationReasonEnum
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="list-communication-reason-enum-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.listCommunicationReasonEnum.cr')}
                id="list-communication-reason-enum-cr"
                name="cr"
                data-cy="cr"
                type="select"
              >
                <option value="Missing_info">{translate('hcpNphiesPortalApp.CommunicationReasonEnum.Missing_info')}</option>
                <option value="Missing_attach">{translate('hcpNphiesPortalApp.CommunicationReasonEnum.Missing_attach')}</option>
                <option value="Info_correct">{translate('hcpNphiesPortalApp.CommunicationReasonEnum.Info_correct')}</option>
              </ValidatedField>
              <ValidatedField
                id="list-communication-reason-enum-communication"
                name="communicationId"
                data-cy="communication"
                label={translate('hcpNphiesPortalApp.listCommunicationReasonEnum.communication')}
                type="select"
              >
                <option value="" key="0" />
                {communications
                  ? communications.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/list-communication-reason-enum"
                replace
                color="info"
              >
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ListCommunicationReasonEnumUpdate;
