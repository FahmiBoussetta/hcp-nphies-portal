import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICommunication } from 'app/shared/model/communication.model';
import { getEntities as getCommunications } from 'app/entities/communication/communication.reducer';
import { getEntity, updateEntity, createEntity, reset } from './list-communication-reason-enum.reducer';
import { IListCommunicationReasonEnum } from 'app/shared/model/list-communication-reason-enum.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IListCommunicationReasonEnumUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ListCommunicationReasonEnumUpdate = (props: IListCommunicationReasonEnumUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { listCommunicationReasonEnumEntity, communications, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/list-communication-reason-enum');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCommunications();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...listCommunicationReasonEnumEntity,
        ...values,
        communication: communications.find(it => it.id.toString() === values.communicationId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
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
            <AvForm model={isNew ? {} : listCommunicationReasonEnumEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="list-communication-reason-enum-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="list-communication-reason-enum-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="crLabel" for="list-communication-reason-enum-cr">
                  <Translate contentKey="hcpNphiesPortalApp.listCommunicationReasonEnum.cr">Cr</Translate>
                </Label>
                <AvInput
                  id="list-communication-reason-enum-cr"
                  data-cy="cr"
                  type="select"
                  className="form-control"
                  name="cr"
                  value={(!isNew && listCommunicationReasonEnumEntity.cr) || 'Missing_info'}
                >
                  <option value="Missing_info">{translate('hcpNphiesPortalApp.CommunicationReasonEnum.Missing_info')}</option>
                  <option value="Missing_attach">{translate('hcpNphiesPortalApp.CommunicationReasonEnum.Missing_attach')}</option>
                  <option value="Info_correct">{translate('hcpNphiesPortalApp.CommunicationReasonEnum.Info_correct')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="list-communication-reason-enum-communication">
                  <Translate contentKey="hcpNphiesPortalApp.listCommunicationReasonEnum.communication">Communication</Translate>
                </Label>
                <AvInput
                  id="list-communication-reason-enum-communication"
                  data-cy="communication"
                  type="select"
                  className="form-control"
                  name="communicationId"
                >
                  <option value="" key="0" />
                  {communications
                    ? communications.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/list-communication-reason-enum" replace color="info">
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
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  communications: storeState.communication.entities,
  listCommunicationReasonEnumEntity: storeState.listCommunicationReasonEnum.entity,
  loading: storeState.listCommunicationReasonEnum.loading,
  updating: storeState.listCommunicationReasonEnum.updating,
  updateSuccess: storeState.listCommunicationReasonEnum.updateSuccess,
});

const mapDispatchToProps = {
  getCommunications,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ListCommunicationReasonEnumUpdate);
