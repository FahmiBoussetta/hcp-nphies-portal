import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IItem } from 'app/shared/model/item.model';
import { getEntities as getItems } from 'app/entities/item/item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './diagnosis-sequence.reducer';
import { IDiagnosisSequence } from 'app/shared/model/diagnosis-sequence.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDiagnosisSequenceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DiagnosisSequenceUpdate = (props: IDiagnosisSequenceUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { diagnosisSequenceEntity, items, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/diagnosis-sequence');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getItems();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...diagnosisSequenceEntity,
        ...values,
        item: items.find(it => it.id.toString() === values.itemId.toString()),
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
          <h2 id="hcpNphiesPortalApp.diagnosisSequence.home.createOrEditLabel" data-cy="DiagnosisSequenceCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.diagnosisSequence.home.createOrEditLabel">
              Create or edit a DiagnosisSequence
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : diagnosisSequenceEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="diagnosis-sequence-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="diagnosis-sequence-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="diagSeqLabel" for="diagnosis-sequence-diagSeq">
                  <Translate contentKey="hcpNphiesPortalApp.diagnosisSequence.diagSeq">Diag Seq</Translate>
                </Label>
                <AvField id="diagnosis-sequence-diagSeq" data-cy="diagSeq" type="string" className="form-control" name="diagSeq" />
              </AvGroup>
              <AvGroup>
                <Label for="diagnosis-sequence-item">
                  <Translate contentKey="hcpNphiesPortalApp.diagnosisSequence.item">Item</Translate>
                </Label>
                <AvInput id="diagnosis-sequence-item" data-cy="item" type="select" className="form-control" name="itemId">
                  <option value="" key="0" />
                  {items
                    ? items.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/diagnosis-sequence" replace color="info">
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
  items: storeState.item.entities,
  diagnosisSequenceEntity: storeState.diagnosisSequence.entity,
  loading: storeState.diagnosisSequence.loading,
  updating: storeState.diagnosisSequence.updating,
  updateSuccess: storeState.diagnosisSequence.updateSuccess,
});

const mapDispatchToProps = {
  getItems,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DiagnosisSequenceUpdate);
